package org.sz.mbay.channel.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.MsgType;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.context.ChannelConfig;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.wechat.service.WeChatCampaignService;

/**
 * @Description: 微信营销流量充值
 * @author han.han
 * @date 2014-11-13 下午3:14:48
 */
@Controller
@RequestMapping("eventtraffic")
public class WeChatCampaignAPIAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WeChatCampaignAPIAction.class);
	
	@Autowired
	private WeChatCampaignService service;
	
	// 钱包接口地址
	private static String WALLET_INTERFACE = ChannelConfig.getWalletInterface();
	
	// 钱包 - 券包 - 立即兑换
	private static final String EXCHANGE_TRAFFIC_URL = WALLET_INTERFACE 
			+ "app/exchange_ticket/exchange_traffic_outside.mbay";
	
	// 钱包 - 券包 - 钱包领取
	private static final String EXCHANGE_TOWALLET_URL = WALLET_INTERFACE 
			+ "app/exchange_ticket/exchange_mbaywallet_outside.mbay";
	
	/**
	 * 活动流量充值
	 * 
	 * @param eventnumber
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public String gettraffic(@RequestParam("eventnumber") String eventnumber,
			@RequestParam("mobile") String mobile,
			@RequestParam(value = "tk", required = false) String ticketId,
			HttpServletRequest request, HttpServletResponse response) {
		String joined_mark = "joined_mark_" + eventnumber;
		// 判断cookie是否存在
		if (RequestUtil.getCookie(request, joined_mark) != null) {
			return JSONObject.fromObject(
					new RedeemResponse("EVENT_ISHAVE_ERROR",
							"您已参加过此活动,感谢您的参与!", MsgType.TEXT)).toString();
		}
		// /判断活动编号是否正确
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			return JSONObject.fromObject(
					new RedeemResponse("EVENTNUMBER_ERROR", "活动编号错误",
							MsgType.TEXT)).toString();
		}
		// 运营商活动界面 - 直接兑换流量
		if (StringUtils.isEmpty(ticketId)) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("接收到流量兑换请求，活动编号：" + eventnumber + ",手机号：" + mobile);
			}
			RedeemResponse redemResponse = service.rechargeTraffic(eventnumber,
					mobile);
			if (redemResponse.isSuccess()) {
				// 设置cookie 2个月 防止单用户用其它号码多次参与
				RequestUtil.setCookie(response, joined_mark, eventnumber,
						60 * 60 * 24 * 60);
			}
			JSONObject object = JSONObject.fromObject(redemResponse);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("活动编号：" + eventnumber + ",手机号：" + mobile + "兑换结果："
						+ object.toString());
			}
			request.getSession().removeAttribute("TEMPLATE_REQUEST_URL");
			return object.toString();
		} else {// 美贝钱包兑换券 - 分享 - 直接兑换流量
				// 请求美贝钱包兑换流量接口
			String url = EXCHANGE_TRAFFIC_URL
					+ "?exchangeTicketId=" + ticketId + "&mobile=" + mobile;
			try {
				String result = HttpSupport.build(url).connect();
				JSONObject obj = JSONObject.fromObject(result);
				RedeemResponse red;
				if (obj.getBoolean("status")) {
					red = new RedeemResponse(true, null, "兑换成功", MsgType.TEXT);
				} else {
					red = new RedeemResponse(false, obj.getString("errorCode"),
							obj.getString("errorMsg"), MsgType.TEXT);
				}
				if (red.isSuccess()) {// 设置cookie
					RequestUtil.setCookie(response, joined_mark, eventnumber,
							60 * 60 * 24 * 60);
				}
				return JSONObject.fromObject(red).toString();
			} catch (Exception e) {
				LOGGER.error("EventRechargeTrafficAction gettraffic error:"
						+ e.fillInStackTrace());
				return JSONObject.fromObject(
						new RedeemResponse("TICKET_EXCHANGE_FAIL", "兑换券兑换流量失败",
								MsgType.TEXT)).toString();
			}
		}
	}
	
	/**
	 * 通过美贝钱包兑换
	 * 
	 * @param eventnumber
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getByWallet")
	public JSONObject getByWallet(
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("mobile") String mobile,
			@RequestParam(value = "tk", required = false) String ticketId) {
		eventnumber = DigestUtils.pbeDecrypt(eventnumber);
		if (eventnumber == null) {
			return JSONObject.fromObject(new RedeemResponse(
					"EVENTNUMBER_ERROR", "活动编号错误", MsgType.TEXT));
		}
		
		// 运营商活动界面 - 美贝钱包领取
		if (StringUtils.isEmpty(ticketId)) {
			RedeemResponse response = service.rechargeTrafficByWallet(
					eventnumber, mobile);
			JSONObject object = JSONObject.fromObject(response);
			return object;
		}
		// 美贝钱包兑换券 - 分享 - 美贝钱包领取
		else {
			// 请求美贝钱包领取接口
			String url = EXCHANGE_TOWALLET_URL
					+ "?exchangeTicketId=" + ticketId + "&mobile=" + mobile;
			try {
				String result = HttpSupport.build(url).connect();
				JSONObject obj = JSONObject.fromObject(result);
				RedeemResponse red;
				if (obj.getBoolean("status")) {
					red = new RedeemResponse(true, null, null, MsgType.TEXT);
				} else {
					red = new RedeemResponse(false, obj.getString("errorCode"),
							obj.getString("errorMsg"), MsgType.TEXT);
				}
				return JSONObject.fromObject(red);
			} catch (Exception e) {
				LOGGER.error("EventRechargeTrafficAction getByWallet error:"
						+ e.fillInStackTrace());
				return JSONObject.fromObject(new RedeemResponse(
						"TICKET_EXCHANGE_FAIL", "使用美贝钱包领取失败", MsgType.TEXT));
			}
		}
	}
	
	/**
	 * 根据订单号查询订单充值信息
	 * 
	 * @param ordernumber
	 * @return
	 */
	@RequestMapping("chargeResult")
	@ResponseBody
	public String chargeResult(@RequestParam("ordernumber") String ordernumber) {
		// TODO:1
		Map<String, Object> map = new HashMap<String, Object>();
		
		// TrafficOrder order = trafficOrderService
		// .findTrafficOrder(ordernumber);
		// if (order == null) {
		// map.put("status", "ERROR");
		// map.put("content", "未查到流量充值信息");
		// return JSONObject.fromObject(map).toString();
		// }
		// // 流量充值成功
		// if (order.getStatus().equals(TrafficOrderStatus.RECHARGE_SUCCESS)) {
		// map.put("status", "SUCCESS");
		// map.put("content", "");
		// }
		// // 流量充值失败
		// else if
		// (order.getStatus().equals(TrafficOrderStatus.RECHARGE_FAILED)) {
		// map.put("status", "FAIL");
		// TrafficOrderRecord record = trafficOrderService
		// .findFailChargeOrderRecord(ordernumber);
		// map.put("content", record.getContent());
		// }
		// // 流量充值中
		// else {
		// map.put("status", "RECHARGEING");
		// map.put("content", "流量充值中....");
		// }
		
		return JSONObject.fromObject(map).toString();
	}
}
