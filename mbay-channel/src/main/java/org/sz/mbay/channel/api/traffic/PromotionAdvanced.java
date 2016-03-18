package org.sz.mbay.channel.api.traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.base.wrap.MsgType;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.channel.framework.action.BaseAction;
import org.sz.mbay.channel.util.AdvancedResult;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.promotion.bean.PromotionCampaignAdvanced;
import org.sz.mbay.promotion.enums.CodeEndType;
import org.sz.mbay.promotion.qo.RedeemCodeForm;
import org.sz.mbay.promotion.service.PromotionCampaignService;
import org.sz.mbay.promotion.service.RedeemCodeService;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @Description: 促销神器-开发者模式
 * @author frank.zong
 * @date 2015-7-9 下午15:14:40
 * 		
 */
@Controller
@RequestMapping("api/promotion/advanced")
public class PromotionAdvanced extends BaseAction {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionAdvanced.class);
			
	private ListeningExecutorService executorService = MoreExecutors
			.listeningDecorator(Executors.newCachedThreadPool());
	private RateLimiter limiter = RateLimiter.create(50.0);
	
	@Autowired
	PromotionCampaignService service;
	@Autowired
	RedeemCodeService codeService;
	
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
	public JSONObject advanced(@RequestParam("userNumber") String userNumber,
			@RequestParam("campaignNumber") String campaignNumber,
			@RequestParam("timeType") int timeType,
			@RequestParam("time") String time,
			@RequestParam("count") int count, @RequestParam("type") int type,
			@RequestParam("code") String code,
			@RequestParam("mobile") String mobile,
			@RequestParam("sign") String sign) {
		if (limiter.tryAcquire()) {
			TrafficTask task = new TrafficTask(userNumber, campaignNumber,
					timeType, time, count, type, code, mobile, sign);
			ListenableFuture<JSONObject> listenableFuture = executorService
					.submit(task);
			try {
				return listenableFuture.get();
			} catch (Exception e) {
				LOGGER.error(
						"PromotionAdvanced advanced error:" + e.getMessage());
				return JSONObject.fromObject(RedeemResponse.SYSTEMERROR);
			}
		} else {
			RedeemResponse resp = new RedeemResponse(false, "SERVER_BUSY",
					"服务器繁忙，请稍后再试", MsgType.TEXT);
			return JSONObject.fromObject(resp);
		}
		
	}
	
	/**
	 * 流量充值任务
	 */
	private class TrafficTask implements Callable<JSONObject> {
		
		private String userNumber;
		private String campaignNumber;
		private int timeType;
		private String time;
		private int count;
		private int type;
		private String code;
		private String mobile;
		private String sign;
		
		public TrafficTask(String userNumber, String campaignNumber,
				int timeType, String time, int count, int type, String code,
				String mobile, String sign) {
			this.userNumber = userNumber;
			this.campaignNumber = campaignNumber;
			this.timeType = timeType;
			this.time = time;
			this.count = count;
			this.type = type;
			this.code = code;
			this.mobile = mobile;
			this.sign = sign;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public JSONObject call() throws Exception {
			List<String> code_list = null;
			List<String> mobile_list = null;
			LOGGER.info("===========【参数验证 start】=========");
			if (StringUtils.isEmpty(userNumber)
					|| StringUtils.isEmpty(campaignNumber)
					|| StringUtils.isEmpty(time)
					|| StringUtils.isEmpty(code)
					|| StringUtils.isEmpty(sign)) {
				LOGGER.error(
						"【参数不正确】 -> userNumber:{}; campaignNumber:{}; time:{}; code:{}; sign:{}",
						userNumber, campaignNumber, time, code, sign);
				return JSONObject.fromObject(AdvancedResult.ERROR_URL);
			}
			// 截止时间类型目前只支持2种->0.截止时间 1.截止小时
			if (timeType > 1 && timeType < 0) {
				LOGGER.error("【截止时间类型目前只支持2种:0,1】 -> timeType:{}", timeType);
				return JSONObject.fromObject(AdvancedResult.ERROR_URL);
			}
			// 区分类型目前只支持2种->0.只有兑换码 1.兑换码+手机号
			if (type > 1 && type < 0) {
				LOGGER.error("【区分类型目前只支持2种:0,1】 -> type:{}", type);
				return JSONObject.fromObject(AdvancedResult.ERROR_URL);
			}
			// 目前最多支持10个
			if (count > 10 && count <= 0) {
				LOGGER.error("【数量过大,目前最多支持10个】 -> count:{}", count);
				return JSONObject.fromObject(AdvancedResult.ERROR_URL);
			}
			// 兑换码(json转list)
			JSONArray code_json = (JSONArray) JSONSerializer.toJSON(code);
			code_list = (List) JSONSerializer.toJava(code_json);
			// 判断兑换码个数是否为0
			if (code_list.size() == 0) {
				LOGGER.error("【兑换码个数为0】 -> code:{}", code);
				return JSONObject.fromObject(AdvancedResult.ERROR_URL);
			}
			// 如果类型为1则表示需要验证手机号
			if (type == 1) {
				if (StringUtils.isEmpty(mobile)) {
					LOGGER.error("【参数不正确】 -> mobile:{}", mobile);
					return JSONObject.fromObject(AdvancedResult.ERROR_URL);
				}
				// 手机号(json转list)
				JSONArray mobile_json = (JSONArray) JSONSerializer
						.toJSON(mobile);
				mobile_list = (List) JSONSerializer.toJava(mobile_json);
				// 判断兑换码个数与手机号个数是否一致
				if (code_list.size() != mobile_list.size()) {
					LOGGER.error("【手机号与兑换码个数不一致】 -> code:{}; mobile:{}", code,
							mobile);
					return JSONObject.fromObject(AdvancedResult.ERROR_URL);
				}
			}
			// 判断数量跟兑换码个数是否一致
			if (code_list.size() != count) {
				LOGGER.error("【数量与兑换码个数不一致】 -> count:{}; code_size:{}", count,
						code_list.size());
				return JSONObject.fromObject(AdvancedResult.ERROR_URL);
			}
			LOGGER.info("===========【参数验证 end】=========");
			
			// 1.活动验证
			LOGGER.info("===========【活动验证 start】=========");
			PromotionCampaignAdvanced advanced = service
					.findCampaignAdvanced(campaignNumber);
			if (advanced == null) {
				LOGGER.error("【活动编号不正确】 -> 活动编号:{}", campaignNumber);
				return JSONObject
						.fromObject(AdvancedResult.ERROR_CAMPAIGN_FAIL);
			} else if (EnableState.DISENABLE.equals(advanced.getStatus())) {
				LOGGER.error("【活动已禁用】 -> 活动编号:{}", campaignNumber);
				return JSONObject
						.fromObject(AdvancedResult.ERROR_CAMPAIGN_DISABLED);
			}
			LOGGER.info("===========【活动验证 end】=========");
			
			// 2.密钥验证
			LOGGER.info("===========【密钥验证 start】=========");
			// token
			String token = advanced.getToken();
			String str = DigestUtils.md5Encrypt(userNumber + campaignNumber
					+ timeType + time + count + type + code + mobile + token);
			if (!sign.equals(str)) {
				LOGGER.error(
						"【密钥不正确】 -> userNumber:{}; campaignNumber:{}; timeType:{}; time:{}; count:{}; type:{}; code:{}; mobile:{}; token:{}",
						userNumber, campaignNumber, timeType, time, count, type,
						code, mobile, token);
				return JSONObject.fromObject(AdvancedResult.ERROR_PID);
			}
			LOGGER.info("===========【密钥验证 end】=========");
			
			// 3.兑换码生成
			LOGGER.info("===========【兑换码生成 start】=========");
			List<RedeemCodeForm> list = new ArrayList<RedeemCodeForm>();
			for (int i = 0; i < code_list.size(); i++) {
				RedeemCodeForm form = new RedeemCodeForm();
				form.setCode(code_list.get(i));
				form.setTime(time);
				if (mobile_list != null && mobile_list.size() > 0) {
					form.setMobile(mobile_list.get(i));
				}
				list.add(form);
			}
			ExecuteResult result = codeService.generateRedeemCodeByMunual(
					campaignNumber, list, CodeEndType.values()[timeType]);
			if (!result.isSuccess()) {
				LOGGER.error("兑换码生成失败,原因:" + result.getMessage());
				return JSONObject.fromObject(AdvancedResult.SYSTEM_ERROR);
			}
			LOGGER.info("===========【兑换码生成 end】=========");
			
			// 考虑到区别兑换码是开发者模式导入的还是编辑模式的,故此如果是开发者模式则不会领取兑换码
			// (意味着领取数量不会增加、领取时间不会更改、兑换码状态不会更改为已领取)
			/*LOGGER.info("===========【兑换码领取 start】=========");
			// 4.兑换码领取
			ExecuteResult get_result = codeService
					.generateRedeemCode(campaignNumber);
			if (!get_result.isSuccess()) {
				LOGGER.error("兑换码领取失败,原因:" + get_result.getMessage());
				return JSONObject.fromObject(AdvancedResult.SYSTEM_ERROR);
			}
			LOGGER.info("===========【兑换码领取 end】=========");*/
			
			if (mobile_list != null && mobile_list.size() > 0) {
				// 5.立即兑换(只有传入手机号的时候才会执行)
				LOGGER.info("===========【立即兑换 start】=========");
				RedeemResponse response = codeService.codeRedemMbay(
						campaignNumber, list);
				LOGGER.info("===========【立即兑换 end】=========");
				if (!response.isSuccess()) {
					LOGGER.error("兑换码立即兑换失败,原因:" + response.getContent());
					return JSONObject.fromObject(AdvancedResult.SYSTEM_ERROR);
				}
			}
			return JSONObject.fromObject(AdvancedResult.SUCCESS);
		}
	}
}
