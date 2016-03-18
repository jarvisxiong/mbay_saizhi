package org.sz.mbay.channel.api.traffic;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.wrap.MsgType;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.util.AdvancedResult;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.wechat.bean.WeChatCampaignAdvanced;
import org.sz.mbay.wechat.service.WeChatCampaignService;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

/**
 * @Description: 开发者模式
 * @author frank.zong
 * @date 2014-12-2 下午15:14:40
 * 
 */
@Controller
@RequestMapping("api/wechart/advanced")
public class WeChartAdvanced extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeChartAdvanced.class);
	
	private ListeningExecutorService executorService = MoreExecutors
			.listeningDecorator(Executors.newCachedThreadPool());
	private RateLimiter limiter = RateLimiter.create(50.0);
	
	@Autowired
	WeChatCampaignService service;
	
	/**
	 * 开发者模式接口
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param userNumber
	 *            用户编号
	 * @param sign
	 *            签名
	 * @param mobile
	 *            手机号
	 * @return json
	 */
	@RequestMapping
	@ResponseBody
	public JSONObject advanced(String campaignNumber, String userNumber, String sign, String mobile) {
		if (limiter.tryAcquire()) {
			ListenableFuture<JSONObject> listenableFuture =
					executorService.submit(new TrafficTask(campaignNumber, userNumber, sign, mobile));
			try {
				return listenableFuture.get();
			} catch (Exception e) {
				LOGGER.error("WeChartAdvancedAction advanced error:" + e.getMessage());
				return JSONObject.fromObject(RedeemResponse.SYSTEMERROR);
			}
		} else {
			RedeemResponse resp = new RedeemResponse(false, "SERVER_BUSY", "服务器繁忙，请稍后再试", MsgType.TEXT);
			return JSONObject.fromObject(resp);
		}
		
	}
	
	/**
	 * 流量充值任务
	 */
	private class TrafficTask implements Callable<JSONObject> {
		
		private String campaignNumber;
		private String userNumber;
		private String sign;
		private String mobile;
		
		public TrafficTask(String campaignNumber, String userNumber, String sign, String mobile) {
			this.campaignNumber = campaignNumber;
			this.userNumber = userNumber;
			this.sign = sign;
			this.mobile = mobile;
		}
		
		@Override
		public JSONObject call() throws Exception {
			LOGGER.info("===========【参数验证 start】=========");
			if (StringUtils.isEmpty(campaignNumber) || StringUtils.isEmpty(userNumber) ||
					StringUtils.isEmpty(sign) || StringUtils.isEmpty(mobile)) {
				LOGGER.error("【参数不正确】 -> eventnumber:{}; usernumber:{}; sign:{}; mobile:{}", campaignNumber, userNumber, sign, mobile);
				return JSONObject.fromObject(AdvancedResult.ERROR_URL);
			}
			LOGGER.info("===========【参数验证 end】=========");
			
			// 1.活动验证
			LOGGER.info("===========【活动验证 start】=========");
			WeChatCampaignAdvanced advanced = service.findCampaignAdvanced(campaignNumber);
			if (advanced == null) {
				LOGGER.error("【活动编号不正确】 -> 活动编号:{}", campaignNumber);
				return JSONObject.fromObject(AdvancedResult.ERROR_CAMPAIGN_FAIL);
			} else if (EnableState.DISENABLE.equals(advanced.getStatus())) {
				LOGGER.error("【活动已禁用】 -> 活动编号:{}", campaignNumber);
				return JSONObject.fromObject(AdvancedResult.ERROR_CAMPAIGN_DISABLED);
			}
			LOGGER.info("===========【活动验证 end】=========");
			
			// 2.密钥验证
			LOGGER.info("===========【密钥验证 start】=========");
			// token
			String token = advanced.getToken();
			String str = DigestUtils.md5Encrypt(campaignNumber + userNumber + token + mobile);
			if (!sign.equals(str)) {
				LOGGER.error("【密钥不正确】 -> eventnumber:{}; usernumber:{}; token:{}; mobile:{}",
						campaignNumber, userNumber, token, mobile);
				return JSONObject.fromObject(AdvancedResult.ERROR_PID);
			}
			LOGGER.info("===========【密钥验证 end】=========");
			
			// 3.活动流量充值
			LOGGER.info("===========【活动流量充值 start】=========");
			RedeemResponse response = service.rechargeTraffic(campaignNumber, mobile);
			JSONObject object = JSONObject.fromObject(response);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("活动编号：" + campaignNumber + ",手机号：" + mobile + "兑换结果：" + object.toString());
			}
			LOGGER.info("===========【活动流量充值 end】=========");
			return object;
		}
	}
}
