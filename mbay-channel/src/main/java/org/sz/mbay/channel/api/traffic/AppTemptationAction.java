package org.sz.mbay.channel.api.traffic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.apptemptation.bean.AppTemptation;
import org.sz.mbay.apptemptation.bean.AppTemptationAdvanced;
import org.sz.mbay.apptemptation.bean.AppTemptationBindIp;
import org.sz.mbay.apptemptation.bean.AppTemptationIpWhiteList;
import org.sz.mbay.apptemptation.bean.AppTemptationStrategy;
import org.sz.mbay.apptemptation.service.AppTemptationBindIpService;
import org.sz.mbay.apptemptation.service.AppTemptationIpWhiteListService;
import org.sz.mbay.apptemptation.service.AppTemptationService;
import org.sz.mbay.base.annotation.log.CalTimeConsuming;
import org.sz.mbay.base.area.Area;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.enums.SendWay;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.base.wrap.MsgType;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.user.enums.TradeType;
import org.sz.mbay.channel.user.exception.UserAccountTradeException;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.channel.util.AdvancedResult;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.hcode.MbayHcode;
import org.sz.mbay.hcode.bean.HcodeInfo;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.operator.service.OperatorService;
import org.sz.mbay.sms.client.MbaySMS;
import org.sz.mbay.sms.template.enums.SMSType;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

/**
 * 开发者模式
 * 
 * @author jerry
 */
@Controller("API_AppTemptationAction")
@RequestMapping("api/app_temptation")
public class AppTemptationAction extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppTemptationAction.class);
			
	private ListeningExecutorService executorService = MoreExecutors
			.listeningDecorator(Executors.newCachedThreadPool());
	private RateLimiter limiter = RateLimiter.create(50.0);
	
	@Autowired
	private AppTemptationService appTemptationService;
	@Autowired
	private AppTemptationBindIpService appTemptationBindIpService;
	@Autowired
	private TrafficRechargeService trafficRechargeService;
	@Autowired
	private TrafficOrderService trafficOrderService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private AppTemptationIpWhiteListService appTemptationIpWhiteListService;
	
	/**
	 * 开发者模式接口
	 * 
	 * @param eventnumber
	 *            活动编号
	 * @param usernumber
	 *            用户编号
	 * @param sign
	 *            签名
	 * @param mobile
	 *            手机号
	 * @return json
	 */
	@ResponseBody
	@CalTimeConsuming
	@RequestMapping("advanced")
	public Object advanced(
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("usernumber") String usernumber,
			@RequestParam("sign") String sign,
			@RequestParam("mobile") String mobile,
			HttpServletRequest request) {
		if (limiter.tryAcquire()) {
			AppTemptationTrafficTask task = new AppTemptationTrafficTask(
					eventnumber, usernumber, sign, mobile, request);
			ListenableFuture<JSONObject> listenableFuture = executorService
					.submit(task);
			try {
				return listenableFuture.get();
			} catch (Exception e) {
				LOGGER.error("AppTemptationAction advanced error:"
						+ e.getMessage());
				return JSONObject.fromObject(RedeemResponse.SYSTEMERROR);
			}
		} else {
			return new RedeemResponse(false, "SERVER_BUSY",
					"服务器繁忙", MsgType.TEXT);
		}
	}
	
	/**
	 * 查询订单信息
	 * 
	 * @param number
	 * @return
	 */
	@ResponseBody
	@CalTimeConsuming
	@RequestMapping("query")
	public Object query(
			@RequestParam("eventnumber") String eventnumber,
			@RequestParam("usernumber") String usernumber,
			@RequestParam("sign") String sign,
			@RequestParam("ordernumber") String ordernumber,
			HttpServletRequest request) {
		JSONObject data = new JSONObject();
		
		// 参数非空验证
		if (StringUtils.isEmpty(eventnumber)
				|| StringUtils.isEmpty(usernumber)
				|| StringUtils.isEmpty(sign)
				|| StringUtils.isEmpty(ordernumber)) {
			data.put("success", false);
			data.put("errorCode", "ERROR_URL");
			data.put("errorMsg", "请求格式不正确");
			return data;
		}
		
		// 活动验证
		AppTemptationAdvanced advanced = appTemptationService
				.findAppTemptationAdvanced(eventnumber);
		if (advanced == null) {
			data.put("success", false);
			data.put("errorCode", "ERROR_CAMPAIGN_NUMBER");
			data.put("errorMsg", "活动编号不正确");
			return data;
		}
		
		// ip验证
		if (!checkIP(eventnumber, usernumber, request)) {
			data.put("success", false);
			data.put("errorCode", "ILLEGAL_URL");
			data.put("errorMsg", "非法的ip地址");
			return data;
		}
		
		// 密钥验证
		String token = advanced.getToken();
		String str = DigestUtils.md5Encrypt(eventnumber + usernumber + token
				+ ordernumber);
		if (!sign.equals(str)) {
			data.put("success", false);
			data.put("errorCode", "ERROR_PID");
			data.put("errorMsg", "密钥验证不正确");
			return data;
		}
		
		// 订单号验证
		TrafficOrder order = trafficOrderService.findTrafficOrder(ordernumber);
		if (order == null) {
			data.put("success", false);
			data.put("errorCode", "ERROR_NUMBER");
			data.put("errorMsg", "订单编号错误");
			return data;
		}
		
		// 订单不是当前活动创建的
		if (!eventnumber.equals(order.getRelateNumber())) {
			data.put("success", false);
			data.put("errorCode", "NUMBER_MISMATCHING");
			data.put("errorMsg", "订单编号不属于当前活动");
			return data;
		}
		
		// 查询数据
		if (order.getOrs() == null) {
			order.setOrs(OperatorRechargeStatus.NON);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("ordernumber", ordernumber);
		params.put("eventnumber", order.getRelateNumber());
		params.put("mobile", order.getMobile());
		params.put("code", order.getOrs());
		params.put("description", order.getOrs().getValue());
		params.put("traffic", order.getTrafficPackage().getTraffic());
		params.put("time", order.getCreateTime().toString(
				MbayDateFormat.DateFormatter.timeFormat));
		data.put("success", true);
		data.put("data", params);
		return data;
	}
	
	/*-----------------------------------------------------------------
	 *                          辅助方法
	 *---------------------------------------------------------------*/
	
	/*
	 * 验证ip
	 */
	private boolean checkIP(String eventnumber, String usernumber,
			HttpServletRequest request) {
		// 获取活动设置的ip
		List<AppTemptationBindIp> ipList = appTemptationBindIpService
				.findAppTemptationBindIps(eventnumber);
		if (ipList == null || ipList.isEmpty()) {
			LOGGER.info("usernumber:" + usernumber + "/ip未设置");
			return false;
		}
		
		// 查找ip白名单，如果通过不再验证真实ip
		AppTemptationIpWhiteList wlIp = appTemptationIpWhiteListService
				.findByUserNumber(usernumber);
		if (wlIp != null) {
			for (AppTemptationBindIp ip : ipList) {
				if (ip.getIp().equals(wlIp.getIp())) {
					LOGGER.info("usernumber:" + usernumber + "/白名单ip验证通过");
					return true;
				}
			}
		}
		
		// 真实ip地址验证
		String reqIp = RequestUtil.getIP(request);
		LOGGER.info("usernumber:" + usernumber + "/client-ip:" + reqIp);
		for (AppTemptationBindIp ip : ipList) {
			if (ip.getIp().equals(reqIp)) {
				LOGGER.info("usernumber:" + usernumber + "/真实ip验证通过");
				return true;
			}
		}
		
		return false;
	}
	
	/*------------------------------------------------------------------
	 *                           内部类
	 *----------------------------------------------------------------*/
	
	/**
	 * 流量充值任务
	 * 
	 * @author jerry
	 */
	private class AppTemptationTrafficTask implements Callable<JSONObject> {
		
		private String eventnumber;
		private String usernumber;
		private String sign;
		private String mobile;
		private HttpServletRequest request;
		
		public AppTemptationTrafficTask(
				String eventnumber,
				String usernumber,
				String sign,
				String mobile,
				HttpServletRequest request) {
			this.eventnumber = eventnumber;
			this.usernumber = usernumber;
			this.sign = sign;
			this.mobile = mobile;
			this.request = request;
		}
		
		@Override
		public JSONObject call() throws Exception {
			// 返回值
			AppTemptationResponse resp = new AppTemptationResponse();
			
			// 参数非空验证
			if (StringUtils.isEmpty(eventnumber)
					|| StringUtils.isEmpty(usernumber)
					|| StringUtils.isEmpty(sign)
					|| StringUtils.isEmpty(mobile)) {
				resp.setAdvancedResult(AdvancedResult.ERROR_URL);
				return JSONObject.fromObject(resp);
			}
			
			// 活动验证
			AppTemptationAdvanced advanced = appTemptationService
					.findAppTemptationAdvanced(eventnumber);
			if (advanced == null) {
				resp.setAdvancedResult(AdvancedResult.ERROR_CAMPAIGN_FAIL);
				return JSONObject.fromObject(resp);
			} else if (EnableState.DISENABLE.equals(advanced.getStatus())) {
				resp.setAdvancedResult(AdvancedResult.ERROR_CAMPAIGN_DISABLED);
				return JSONObject.fromObject(resp);
			}
			
			// ip验证
			if (!checkIP(eventnumber, usernumber, request)) {
				LOGGER.info("usernumber:" + usernumber + "/ip验证失败");
				resp.setAdvancedResult(AdvancedResult.ILLEGAL_IP);
				return JSONObject.fromObject(resp);
			}
			
			// 密钥验证
			String token = advanced.getToken();
			String str = DigestUtils.md5Encrypt(eventnumber + usernumber + token
					+ mobile);
			if (!sign.equals(str)) {
				resp.setAdvancedResult(AdvancedResult.ERROR_PID);
				return JSONObject.fromObject(resp);
			}
			
			// 活动流量充值
			resp.setRedeemResponse(recharge());
			return JSONObject.fromObject(resp);
		}
		
		/*
		 * 活动流量充值
		 */
		private RedeemResponse recharge() {
			AppTemptation campaign = appTemptationService
					.findAppTemptationSendInfo(eventnumber);
			if (!CampaignStatus.IN_ACTIVE.equals(campaign.getState())) {
				if (CampaignStatus.NOT_STARTED.equals(campaign.getState())) {
					int intime = MbayDateFormat.nowInTimeRange(
							campaign.getStarttime(), campaign.getEndingtime());
					if (intime != 0) {
						String message = intime < 0 ? "活动还未开始！"
								: "活动已结束！";
						return new RedeemResponse("NOT_IN_TIME", message,
								MsgType.TEXT);
					}
				} else {
					return new RedeemResponse("CAMPAIGN_CANCLED", "活动已取消",
							MsgType.TEXT);
				}
			}
			// 超出不可重复
			if (!campaign.isContinuable()) {
				if (campaign.getSendednum() >= campaign.getQuantity()) {
					return new RedeemResponse("TRAFFIC_EXCHANGE_OVER",
							"已达到总数上限",
							MsgType.TEXT);
				}
			}
			SendWay sendway = campaign.getSendway();
			// 送流量
			if (SendWay.TRAFFIC.equals(sendway)) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("活动赠送类型为：流量");
				}
				ExecuteResult judgeresult = appTemptationService
						.appTemptationJudgeMobileReceiveEnable(eventnumber,
								mobile);
				if (!judgeresult.isSuccess()) {
					return new RedeemResponse("NONE_SUPPORT_MOBILE",
							judgeresult.getMessage(), MsgType.TEXT);
				}
				
				HcodeInfo hcode = MbayHcode.getHcodeInfo(mobile);
				if (hcode == null) {
					return new RedeemResponse("NOT_FOUND_MOBILE",
							"未找到对应的手机号信息", MsgType.TEXT);
				}
				
				Area area = Area.valueOf(hcode.getProvcode());
				OperatorType operator = OperatorType
						.valueOf(hcode.getOperator());
				AppTemptationStrategy strategy = appTemptationService
						.findAppTemptationEventStrategyTrafficInfo(eventnumber,
								area, operator);
				TrafficPackage trafficPackage = strategy.getTrafficPackage();
				
				// 判断是否是全国流量包,如果是,则去看本地流量包是否存在
				Boolean flag = ((trafficPackage.getOperator()
						.getArea().value) == 0)
								? true
								: false;
				if (flag) {
					// 判断本地是否存在流量包,如果存在则使用本地流量包，否则还是走全国流量包
					TrafficPackage traffic_package = operatorService
							.getBestTrafficPackage(hcode,
									trafficPackage.getTraffic());
					if (traffic_package != null) {
						strategy.setTrafficPackage(traffic_package);
					}
				}
				
				// 创建订单
				TrafficRechargeInfo info = new TrafficRechargeInfo();
				info.setMobile(mobile);
				info.setRechargeType(TrafficOrderType.APP_CAMPAIGN);
				info.setUserNumber(usernumber);
				info.setRelationNumber(eventnumber);
				info.setTrafficPackageNumber(
						strategy.getTrafficPackage().getId());
				String orderNumber = trafficOrderService.create(info);
				
				// 账户扣款
				String note = "流量下发";
				try {
					userAccountService.expenditure(usernumber,
							TradeType.APP_CAMPAIGN, orderNumber,
							strategy.getTrafficPackage().getMbayprice(), note);
				} catch (UserAccountTradeException e) {
					LOGGER.error("app诱惑账户扣款失败：{}", e.getMessage());
					return new RedeemResponse(false, "REDUCE_BALANCE_FAIL",
							e.getMessage(), MsgType.TEXT);
				}
				
				// 充值流量
				// 减少活动和用户锁定的美贝
				int executeResult = appTemptationService
						.increEventSentAnaReduLocked(strategy
								.getStrategynumber());
				if (executeResult == 0) {
					return new RedeemResponse(false, "COMPLETE_STATUS_FAIL",
							"修改活动状态失败", MsgType.TEXT);
				}
				
				// 充值流量
				trafficRechargeService.recharge(orderNumber);
				
				// 条件为true时发送短信
				if (campaign.isSendsms()) {
					// 发送短信
					String params = campaign.getEventname() + ","
							+ strategy.getTrafficPackage().getTraffic();
					MbaySMS.sendSMS(SMSType.ACTIVITY, mobile, params);
				}
				
				return new RedeemResponse(true, "TRAFFIC_RECHARGEING",
						orderNumber, MsgType.TEXT);
			}
			return RedeemResponse.TRAFFIC_RECHARGEING;
		}
	}
	
	/**
	 * app诱惑充值接口返回值
	 *
	 * @author jerry
	 */
	public class AppTemptationResponse {
		
		private boolean success;
		private String status;
		private String msgType;
		private String content;
		
		public void setRedeemResponse(RedeemResponse resp) {
			this.status = resp.getStatus();
			this.content = resp.getContent();
			this.msgType = resp.getMsgType();
			this.success = resp.isSuccess();
		}
		
		public void setAdvancedResult(AdvancedResult adv) {
			this.status = adv.getStatus();
			this.content = adv.getContent();
			this.msgType = adv.getMsgType();
			this.success = adv.isSuccess();
		}
		
		public boolean isSuccess() {
			return success;
		}
		
		public void setSuccess(boolean success) {
			this.success = success;
		}
		
		public String getStatus() {
			return status;
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		
		public String getMsgType() {
			return msgType;
		}
		
		public void setMsgType(String msgType) {
			this.msgType = msgType;
		}
		
		public String getContent() {
			return content;
		}
		
		public void setContent(String content) {
			this.content = content;
		}
	}
}
