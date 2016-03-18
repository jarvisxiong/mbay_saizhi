package org.sz.mbay.wallet.action.web;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.annotation.CacheControl;
import org.sz.mbay.base.annotation.CachePolicy;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;
import org.sz.mbay.base.wrap.ResponseSuccess;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.service.OperatorService;
import org.sz.mbay.remote.interfaces.wallet.RIMBAccountUtil;
import org.sz.mbay.remote.interfaces.wallet.RIUserUtil;
import org.sz.mbay.remote.interfaces.wallet.base.RIResponse;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;
import org.sz.mbay.wallet.constant.error.GlobalError;
import org.sz.mbay.wallet.constant.error.HcodeError;
import org.sz.mbay.wallet.constant.error.TrafficPackageError;
import org.sz.mbay.wallet.context.WalletContext;
import org.sz.mbay.wallet.entity.SessionUser;
import org.sz.mbay.wallet.service.TrafficRedeemService;

@Controller("Web_Traffic")
@RequestMapping("web/traffic")
public class TrafficAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TrafficAction.class);
			
	@Autowired
	private TrafficRedeemService trafficRedeemService;
	@Autowired
	private TrafficRechargeService trafficRechargeService;
	@Autowired
	private OperatorService operatorService;
	
	/**
	 * 兑换页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@CacheControl(policy = CachePolicy.NO_CACHE)
	@RequestMapping("package/redeem/ui")
	public String packageRedeemUi(Model model, HttpSession session) {
		SessionUser suser = WalletContext.getSessionUser();
		
		// 获取基本信息
		try {
			RIResponse resp = RIUserUtil
					.requestUserInfoByMobile(suser.getMobile());
			if (resp.isStatus()) {
				model.addAttribute("mbayBalance",
						resp.getData().getDouble("balance"));
			}
		} catch (Exception e) {
			LOGGER.error("get user info error:{}", e.getMessage());
		}
		
		model.addAttribute("UNICOM_500",
				getEncodedPackageId(OperatorType.UNICOM, 500));
		model.addAttribute("MOBILE_500",
				getEncodedPackageId(OperatorType.MOBILE, 500));
		model.addAttribute("TELECOM_500",
				getEncodedPackageId(OperatorType.TELECOM, 500));
				
		// 今天有多少人领取
		long baseTime = DateTime.parse(DateTime.now().toString("yyyy-MM-dd"))
				.getMillis();
		long nowTime = DateTime.now().getMillis();
		long time = nowTime - baseTime < 60000 ? 60000 : nowTime - baseTime;
		long nums = time / 1000 / 60 * 8;
		model.addAttribute("attendNums", nums);
		return "traffic/package/redeem";
	}
	
	/*
	 * 获取某运营商全国某流量包id
	 */
	private String getEncodedPackageId(OperatorType type, int traffic) {
		HcodeInfo hcode = new HcodeInfo();
		hcode.setOperator(type.ordinal());
		hcode.setProvcode(Area.QUANGUO.value);
		TrafficPackage pk = operatorService.getBestTrafficPackage(hcode,
				traffic);
		if (pk != null) {
			return DigestUtils.pbeEncrypt(String.valueOf(pk.getId()));
		}
		return null;
	}
	
	/**
	 * 兑换
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("package/redeem")
	public Object packageRedeem(@RequestParam("packageId") String packageId) {
		String mobile = WalletContext.getSessionUser().getMobile();
		if (StringUtils.isEmpty(mobile)) {
			return GlobalError.SESSION_EXPIRED;
		}
		HcodeInfo hcode = MbayHcode.getHcodeInfo(mobile);
		if (hcode == null) {
			return HcodeError.NOT_FOUND_HCODE;
		}
		
		packageId = DigestUtils.pbeDecrypt(packageId);
		if (packageId == null) {
			return TrafficPackageError.PACKAGEID_ERROR;
		}
		
		TrafficPackage pk = operatorService.findTrafficPackage(Integer
				.parseInt(packageId));
		if (pk == null) {
			return TrafficPackageError.PACKAGE_NOT_FOUND;
		}
		
		// 检测号码运营商类别是否匹配
		if (OperatorType.valueOf(hcode.getOperator()) != pk.getOperator()
				.getType()) {
			return HcodeError.OPERATOR_MISMATCHING;
		}
		
		// 账户扣款&创建流量充值订单
		StringBuffer sb = new StringBuffer();
		sb.append(pk.getOperator().getArea().getName())
				.append(pk.getOperator().getType().getValue())
				.append(pk.getTraffic())
				.append("MB")
				.append(pk.getTrafficType().value)
				.append("直充");
		Response resp = trafficRedeemService.trafficExchange(mobile, pk,
				sb.toString());
		if (!resp.getStatus()) {
			return resp;
		}
		String orderNumber = (String) ((ResponseSuccess) resp).getData();
		LOGGER.info("钱包用户{}请求流量兑换,订单：{}。时间:{}", mobile, orderNumber,
				System.currentTimeMillis());
				
		// 流量充值
		ExecuteResult result = trafficRechargeService.recharge(orderNumber);
		LOGGER.info("钱包用户{}请求流量兑换结束,订单：{}。时间:{}", orderNumber, mobile,
				System.currentTimeMillis());
		if (!result.isSuccess()) {
			LOGGER.error("手机号{}流量兑换失败：", mobile, result.getError_code() + ":"
					+ result.getMessage());
			return ResponseFail.create(result.getError_code(),
					result.getMessage());
		}
		
		// 发送短信
		MbaySMS.sendSMS(SMSType.WL_TRAFFIC_EXCHANGE, mobile,
				pk.getTraffic() + "MB");
				
		// 获取余额
		try {
			RIResponse rd = RIMBAccountUtil.requestUserGetMBQty(mobile);
			return ResponseSuccess.create(rd.getData().getDouble("balance"));
		} catch (Exception e) {
			LOGGER.error("get balance error:{}", e.getMessage());
		}
		return ResponseSuccess.create(0);
	}
}
