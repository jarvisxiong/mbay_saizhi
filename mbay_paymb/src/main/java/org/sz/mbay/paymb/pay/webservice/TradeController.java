package org.sz.mbay.paymb.pay.webservice;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.paymb.pay.annotation.TradePolicy;
import org.sz.mbay.paymb.pay.annotation.TradeType;
import org.sz.mbay.paymb.pay.context.RequestTradeHolder;
import org.sz.mbay.paymb.pay.exception.MBTradeException;
import org.sz.mbay.paymb.pay.parameter.AbstractParameterRequir;
import org.sz.mbay.paymb.pay.parameter.BalanceQueryParameterRequir;
import org.sz.mbay.paymb.pay.parameter.ParameterManger;
import org.sz.mbay.paymb.pay.parameter.PayParameterRequir;
import org.sz.mbay.paymb.pay.parameter.RedeemParameterRequir;
import org.sz.mbay.paymb.pay.response.TradeResponse;
import org.sz.mbay.paymb.pay.service.MBTradeService;
import org.sz.mbay.trafficSign.bean.TrafficSign;
import org.sz.mbay.trafficSign.service.TrafficSignService;

/**
 * 用户MB交易接口。包含MB支付，MB余额查询，用户在第三方平台下的货币反兑为MB
 * 
 * @author han.han
 *
 */
@Controller
@RequestMapping("v1")
public class TradeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	TrafficSignService trafficSignService;

	@Autowired
	MBTradeService tradeService;


	/**
	 * 验证请求参数是否合法
	 * 
	 * @param request
	 */
	@ModelAttribute
	public void validate(HttpServletRequest request) {
		
		String requestIP = RequestUtil.getIP(request);
		
		TradePolicy requestTradePolicy = RequestTradeHolder.getRequesTradePolicy();
		LOGGER.info("接收到IP:{} MB {} 交易请求", requestIP, requestTradePolicy.policy());
		
		// 获取请求参数
		Map<String, String[]> paramenters = request.getParameterMap();
		// 验证请求参数
		LOGGER.info("验证请求信息是否合法");
		boolean isValid = ParameterManger.isMatch(paramenters, requestTradePolicy);
		if (!isValid) {
			LOGGER.warn("IP:{}参数验证不通过！", requestIP);
			throw new MBTradeException(TradeResponse.PARAMETER_ERROR);
		}

		// 验证请求商户是否已在直通车平台签约
		String user_number = request.getParameter(AbstractParameterRequir.USER_NUMBER);
		TrafficSign ts = this.trafficSignService.findTrafficSignByUserNumber(user_number);
		if (ts == null) {
			LOGGER.warn("IP:{}合作伙伴ID：{}不正确！", requestIP, user_number);
			throw new MBTradeException(TradeResponse.ILLEGAL_PARTNER);
		}

		// 验证签名是否正确
		String key = ts.getPid();
		// 请求签名
		String requestSign = request.getParameter(AbstractParameterRequir.SIGN);
		String sign = ParameterManger.signRequest(paramenters, key);
		if (!sign.equals(requestSign)) {
			LOGGER.warn("IP:"+requestIP+"签名不正确。请求签名：{},服务端签名:{}",requestSign, sign);
			throw new MBTradeException(TradeResponse.ILLEGAL_SIGN);
		}

	}

	/**
	 * 用户MB支出交易 用户以MB为货币，参与商城实物购买，确认购买后执行MB支出交易。
	 * 
	 * @param request
	 * @return
	 */
	@TradeType(policy = TradePolicy.PAY)
	@RequestMapping("pay")
	@ResponseBody
	public TradeResponse pay(HttpServletRequest request) {
		String requestIP = RequestUtil.getIP(request);

		String user_number = request.getParameter(PayParameterRequir.USER_NUMBER);
		String mbuid = request.getParameter(PayParameterRequir.MB_UID);
		int mb = Integer.valueOf(request.getParameter(PayParameterRequir.TOTAL_FEE));
		String orderNumber = request.getParameter(PayParameterRequir.OUT_TRADE_NO);
		String desc = "";
		String base64desc = request.getParameter(PayParameterRequir.DESC);
		try {
			if (StringUtils.isNotEmpty(base64desc))
				desc = new String(Base64.decodeBase64(base64desc.getBytes("UTF-8")), "UTF-8");
		} catch (Exception e) {
			LOGGER.error("MB pay Base64.decodeBase64(交易描述) 异常：", e.fillInStackTrace());
			return TradeResponse.PARAMETER_ERROR;
		}
		TradeResponse payResponse = tradeService.expenditure(user_number, mbuid, mb, orderNumber, desc);
		LOGGER.warn("IP:{} pay 交易结果：{}", requestIP, payResponse.toString());
		return payResponse;
	}

	/**
	 * MB用户MB余额查询
	 * 
	 * @param request
	 * @return
	 */
	@TradeType(policy = TradePolicy.BALANCE)
	@RequestMapping("balance")
	@ResponseBody
	public TradeResponse accountBalance(HttpServletRequest request) {
		String requestIP = RequestUtil.getIP(request);
		String mb_ucode = request.getParameter(BalanceQueryParameterRequir.MB_UCODE);
		String ucode = DigestUtils.pbeDecrypt(mb_ucode);
		if (StringUtils.isEmpty(ucode)) {
			return TradeResponse.ILLEGAL_PARTNER;
		}

		TradeResponse response = tradeService.balance(ucode);
		LOGGER.warn("IP:{} balance 交易结果：{}", requestIP, response.toString());
		return response;
	}

	/**
	 * 商家用户反兑MB 用户在第三方平台下拥有独立第三方虚拟货币，并以此货币反兑为MB。此前提需保证第三方用户同时已是MB用户。
	 * 
	 * @param request
	 * @return
	 */
	@TradeType(policy = TradePolicy.REDEEM)
	@RequestMapping("redeem")
	@ResponseBody
	public TradeResponse redeemMB(HttpServletRequest request) {
		String requestIP = request.getRemoteAddr();
		
		String user_number = request.getParameter(RedeemParameterRequir.USER_NUMBER);
		String mbuid = request.getParameter(RedeemParameterRequir.MB_UID);
		int mb = Integer.valueOf(request.getParameter(RedeemParameterRequir.TOTAL_FEE));
		String orderNumber = request.getParameter(RedeemParameterRequir.OUT_TRADE_NO);
		String desc = "";
		String base64desc = request.getParameter(RedeemParameterRequir.DESC);
		try {
			if (StringUtils.isNotEmpty(base64desc))
				desc = new String(Base64.decodeBase64(base64desc.getBytes("UTF-8")), "UTF-8");
		} catch (Exception e) {
			LOGGER.error("MB redeem Base64.decodeBase64(交易描述) 异常：", e.fillInStackTrace());
			return TradeResponse.PARAMETER_ERROR;
		}
		TradeResponse payResponse = tradeService.redeemMB(user_number, mbuid, mb, orderNumber, desc);
		LOGGER.warn("IP:{} redeem 交易结果：{}", requestIP, payResponse.toString());
		return payResponse;
	}

}
