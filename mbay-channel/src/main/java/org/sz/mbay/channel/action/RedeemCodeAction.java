package org.sz.mbay.channel.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.base.regex.pattern.RegExp;
import org.sz.mbay.base.web.utils.RequestUtil;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.base.wrap.RedeemResponse;
import org.sz.mbay.common.util.DigestUtils;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;
import org.sz.mbay.promotion.bean.PromotionCampaign;
import org.sz.mbay.promotion.bean.PromotionCampaignShare;
import org.sz.mbay.promotion.bean.RedeemCode;
import org.sz.mbay.promotion.enums.CodeType;
import org.sz.mbay.promotion.enums.LimitType;
import org.sz.mbay.promotion.enums.RedeemCodeStu;
import org.sz.mbay.promotion.qo.RedeemCodeForm;
import org.sz.mbay.promotion.service.PromotionCampaignService;
import org.sz.mbay.promotion.service.PromotionCampaignShareService;
import org.sz.mbay.promotion.service.RedeemCodeService;

import net.sf.json.JSONObject;

/**
 * @Description: 兑换码兑换流量controller
 * @author han.han
 * @date 2014-11-13 下午3:27:29
 * 
 */
@RequestMapping("redeemcode")
@Controller
public class RedeemCodeAction {

	/**
	 * 数组（除去O和0、I、1和L）
	 */
	private static final char VERIFICATE[] = { '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 兑换码限制
	 */
	private static final String REDEEM_CODE_LIMIT = "redeem_code_limit";

	/**
	 * 兑换码限制次数
	 */
	private static final int REDEEM_CODE_LIMIT_COUNT = 3;

	@Autowired
	RedeemCodeService codeService;
	@Autowired
	PromotionCampaignService campaignService;
	@Autowired
	PromotionCampaignShareService shareService;

	@RequestMapping("queryCode")
	@ResponseBody
	public JSONObject queryCode(@RequestParam String code, @RequestParam String campaignNumber) {
		JSONObject json = new JSONObject();
		// 说明标题
		String queryTitle = "";
		// 说明详情
		String queryContent = "";
		// 兑换码状态(有效、无效)
		boolean status = false;
		// 解密活动编号
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (!StringUtils.isEmpty(campaignNumber)) {
			// 查询兑换码状态
			RedeemCode rcode = codeService.findStatusByCodeAndCampaignNumber(code, campaignNumber);
			if (rcode != null) {
				RedeemCodeStu stu = rcode.getCodeStatus();
				if (!stu.equals(RedeemCodeStu.REDEEMED)) {
					status = true;
				}
				// 查询兑换说明
				PromotionCampaign campaign = campaignService.findQueryInfomation(campaignNumber);
				if (campaign != null) {
					queryTitle = campaign.getQueryTitle();
					queryContent = campaign.getQueryContent();
				}
			}
		}
		json.put("status", status);
		json.put("queryTitle", queryTitle);
		json.put("queryContent", queryContent);
		return json;
	}

	/**
	 * 兑换码兑换流量
	 * 
	 * @param code
	 * @param mobile
	 * @return
	 */
	@RequestMapping("redeem")
	@ResponseBody
	public JSONObject redeem(@RequestParam String code, @RequestParam String mobile,
			@RequestParam String campaignNumber, @RequestParam boolean captcha, @RequestParam String yzm,
			HttpServletRequest request, HttpServletResponse response) {
		// 兑换码限制
		String limit = (String) request.getSession().getAttribute(REDEEM_CODE_LIMIT);
		if (StringUtils.isEmpty(limit)) {
			request.getSession().setAttribute(REDEEM_CODE_LIMIT, String.valueOf(0));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (!RegExp.mobile.matcher(mobile).matches()) {
			map.put("success", false);
			map.put("message", "您输入的手机号不正确！");
			return JSONObject.fromObject(map);
		}
		if (!RegExp.redeemCode.matcher(code).matches()) {
			setLimitSession(request, map);
			map.put("success", false);
			map.put("message", "您输入的兑换码不正确！");
			return JSONObject.fromObject(map);
		}
		campaignNumber = DigestUtils.pbeDecrypt(campaignNumber);
		if (StringUtils.isEmpty(campaignNumber)) {// 活动编号经篡改
			map.put("success", false);
			map.put("message", "活动编号不正确！");
			return JSONObject.fromObject(map);
		}
		// 查询活动相关信息
		PromotionCampaign campaign = campaignService.findPromotionCampaign(campaignNumber);
		LimitType campaignLimitType = campaign.getCampaignLimitType();
		LimitType mobileLimitType = campaign.getMobileLimitType();
		int campaignLimitValue = campaign.getCampaignLimitValue();
		int mobileLimitValue = campaign.getMobileLimitValue();

		// 查询活动cookie值
		Cookie campaignCookie = RequestUtil.getCookie(request, campaignNumber);
		String campaignTime = "";
		int campaignCount = 0;
		if (campaignCookie == null) {
			RequestUtil.setCookie(response, campaignNumber,
					MbayDateFormat.formatDateTime(DateTime.now(), DatePattern.isoDate) + "_" + 0,
					365 * 30 * 24 * 60 * 60);
			campaignTime = MbayDateFormat.formatDateTime(DateTime.now(), DatePattern.isoDate);
		} else {
			String campaignCookieValue = RequestUtil.getCookie(request, campaignNumber).getValue();
			campaignTime = campaignCookieValue.split("_")[0];
			campaignCount = Integer.valueOf(campaignCookieValue.split("_")[1]);
		}
		
		// 查询分享次数
		int shareTimes = 0;
		if(campaign.isShare()){
			shareTimes = queryShareTimes(campaignNumber, request);
		}

		// 如果是不限则不进行限制判断(活动判断)
		if (!campaignLimitType.equals(LimitType.UNLIMITED)) {
			if (campaignLimitType.equals(LimitType.DAY)) {
				// 当前时间在cookie记录时间的一天之后
				if (MbayDateFormat.stringToDate(campaignTime).plusDays(1).isBefore(DateTime.now())) {
					// 重新设置cookie
					RequestUtil.setCookie(response, campaignNumber,
							MbayDateFormat.formatDateTime(DateTime.now(), DatePattern.isoDate) + "_" + 0, 24 * 60 * 60);
					campaignCount = 0;
				}
			}
			if (campaignLimitType.equals(LimitType.WEEK)) {
				// 当前时间在cookie记录时间的一周之后
				if (MbayDateFormat.stringToDate(campaignTime).plusWeeks(1).isBefore(DateTime.now())) {
					// 重新设置cookie
					RequestUtil.setCookie(response, campaignNumber,
							MbayDateFormat.formatDateTime(DateTime.now(), DatePattern.isoDate) + "_" + 0,
							7 * 24 * 60 * 60);
					campaignCount = 0;
				}
			}
			if (campaignLimitType.equals(LimitType.MONTH)) {
				// 当前时间在cookie记录时间的一月之后
				if (MbayDateFormat.stringToDate(campaignTime).plusMonths(1).isBefore(DateTime.now())) {
					// 重新设置cookie
					RequestUtil.setCookie(response, campaignNumber,
							MbayDateFormat.formatDateTime(DateTime.now(), DatePattern.isoDate) + "_" + 0,
							30 * 24 * 60 * 60);
					campaignCount = 0;
				}
			}

			boolean flag = false;
			// 是否开启分享
			if (campaign.isShare()) {
				// 总共可以参与的次数
				int total = campaignLimitValue + shareTimes;
				if (campaignCount >= total) {
					flag = true;
				}
			}else if (campaignCount >= campaignLimitValue) {
				flag = true;
			}
			
			if(flag){
				map.put("success", false);
				map.put("message", "您今天的兑换次数已用完啦！");
				return JSONObject.fromObject(map);
			}
		}
		
		// 如果是不限则不进行限制判断(手机号判断)
		if (!mobileLimitType.equals(LimitType.UNLIMITED)) {
			// 查询手机号限制
			RedeemCodeForm codeForm = new RedeemCodeForm();
			codeForm.setEventNumber(campaignNumber);
			codeForm.setMobile(mobile);
			String startTime = "";
			String endTime = "";
			// 每天
			if (mobileLimitType.equals(LimitType.DAY)) {
				startTime = MbayDateFormat.formatDateTime(DateTime.now(), DatePattern.isoDate) + " 00:00:00";
				endTime = MbayDateFormat.formatDateTime(DateTime.now(), DatePattern.isoDate) + " 23:59:59";
			}
			// 每周
			if (mobileLimitType.equals(LimitType.WEEK)) {
				int value = DateTime.now().getDayOfWeek() - 1;
				startTime = MbayDateFormat.formatDateTime(DateTime.now().minusDays(value), DatePattern.isoDate)
						+ " 00:00:00";
				endTime = MbayDateFormat.formatDateTime(DateTime.now().plusDays(6 - value), DatePattern.isoDate)
						+ " 23:59:59";
			}
			// 每月
			if (mobileLimitType.equals(LimitType.MONTH)) {
				int value = DateTime.now().getDayOfMonth() - 1;
				startTime = MbayDateFormat.formatDateTime(DateTime.now().minusDays(value), DatePattern.isoDate)
						+ " 00:00:00";
				endTime = MbayDateFormat.formatDateTime(DateTime.now().plusMonths(1).minusDays(value + 1),
						DatePattern.isoDate) + " 23:59:59";
			}
			// 活动期间
			if (mobileLimitType.equals(LimitType.ACTIVE)) {
				startTime = MbayDateFormat.formatDateTime(campaign.getStarttime(), DatePattern.isoDate) + " 00:00:00";
				endTime = MbayDateFormat.formatDateTime(campaign.getEndingtime(), DatePattern.isoDate) + " 23:59:59";
			}
			codeForm.setRedeemStartTime(MbayDateFormat.stringToTime(startTime));
			codeForm.setRedeemEndTime(MbayDateFormat.stringToTime(endTime));
			List<RedeemCode> codeList = codeService.findAllRedeemCodeByEventNumber(codeForm, null);
			if (codeList != null) {
				boolean flag = false;
				// 已参与次数
				int count = codeList.size();
				// 是否开启分享
				if (campaign.isShare()) {
					// 总共可以参与的次数
					int total = mobileLimitValue + shareTimes;
					if (count >= total) {
						flag = true;
					}
				} else if (count >= mobileLimitValue) {
					flag = true;
				}
				
				if(flag) {
					map.put("success", false);
					map.put("message", "您的手机号已多次参与过此活动");
					return JSONObject.fromObject(map);
				}
			}
		}

		// 验证兑换码是否存在
		RedeemCode redeemCode = this.codeService.findRedeemCodeByCodeNumber(code, campaignNumber);
		if (redeemCode == null || !redeemCode.getEventnumber().equals(campaignNumber)) {
			setLimitSession(request, map);
			map.put("success", false);
			map.put("message", "无效的兑换码信息");
			return JSONObject.fromObject(map);
		}

		// 验证兑换码是否有效
		ExecuteResult eResult = this.isValidRedeemCode(redeemCode);
		if (!eResult.isSuccess()) {
			map.put("success", false);
			map.put("message", eResult.getMessage());
			return JSONObject.fromObject(map);
		}

		// 验证验证码
		if (captcha) {
			if (StringUtils.isEmpty(yzm) || !yzm.equals(request.getSession().getAttribute("authcode"))) {
				map.put("success", false);
				map.put("captcha", true);
				map.put("message", "验证码不正确");
				return JSONObject.fromObject(map);
			}
		}
		// 清空次数
		request.getSession().removeAttribute(REDEEM_CODE_LIMIT);
		// 清空验证码
		AuthImg.removeAuthcode(request.getSession());
		RedeemResponse rResponse = null;
		CodeType codeType = redeemCode.getCodeType();
		if (codeType.equals(CodeType.MBAY)) {
			List<RedeemCodeForm> list = new ArrayList<RedeemCodeForm>();
			RedeemCodeForm form = new RedeemCodeForm();
			form.setCode(code);
			form.setMobile(mobile);
			list.add(form);
			rResponse = codeService.codeRedemMbay(campaignNumber, list);
		} else if (codeType.equals(CodeType.TRAFFIC)) {
			rResponse = codeService.codeRedemTraffic(redeemCode, mobile);
			// 如果出现msgType等于MBAY的情况，说明实际走的是codeRedeemMbay方法，更改codeType为MBAY
			if (rResponse.getMsgType().equals(CodeType.MBAY.name())) {
				codeType = CodeType.MBAY;
			}
		}
		map.put("success", rResponse.isSuccess());
		map.put("message", rResponse.getContent());
		map.put("codeType", codeType);
		// 如果兑换失败，直接返回错误
		if (!rResponse.isSuccess()) {
			map.put("verificate", false);
			return JSONObject.fromObject(map);
		}
		map.put("verificate", campaign.isVerificate());
		// 是否生成核销码
		if (campaign.isVerificate()) {
			// 生成核销码
			String verificateCode = generateVerificateCode();
			while (true) {
				// 查看活动下是否有当前核销码,如果有重新生成(保证同一活动下核销码不一样)
				if (codeService.isExistVerificateCode(campaignNumber, verificateCode)) {
					verificateCode = generateVerificateCode();
				} else {
					break;
				}
			}
			// 绑定核销码到兑换码记录
			codeService.updateCodeVerificate(code, campaignNumber, verificateCode);
			map.put("verificateCode", verificateCode);
		}
		// 设置活动限制cookie
		campaignCount = campaignCount + 1;
		RequestUtil.setCookie(response, campaignNumber,
				MbayDateFormat.formatDateTime(DateTime.now(), "yyyy-MM-dd") + "_" + campaignCount,
				365 * 30 * 24 * 60 * 60);
		return JSONObject.fromObject(map);
	}

	private ExecuteResult isValidRedeemCode(RedeemCode redeemCode) {
		if (RedeemCodeStu.REDEEMED.equals(redeemCode.getCodeStatus())) {
			return new ExecuteResult(false, "此兑换码已使用！");
		}
		if (DateTime.now().isBefore(redeemCode.getStarttime())) {
			return new ExecuteResult(false, "活动还未开始，兑换码暂不可使用！");
		}
		if (DateTime.now().isAfter(redeemCode.getExpiretime())) {
			return new ExecuteResult(false, "兑换码已过期！");
		}
		return new ExecuteResult(true, "");
	}

	/**
	 * 生成核销码(4位)
	 */
	private String generateVerificateCode() {
		int length = VERIFICATE.length - 1;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			int value = (int) Math.floor(Math.random() * length);
			sb.append(VERIFICATE[value]);
		}
		return sb.toString();
	}

	/**
	 * 超过一定次数失败显示验证码
	 * 
	 * @param request
	 * @param map
	 */
	private void setLimitSession(HttpServletRequest request, Map<String, Object> map) {
		int new_limit = Integer.parseInt((String) request.getSession().getAttribute(REDEEM_CODE_LIMIT)) + 1;
		if (new_limit > REDEEM_CODE_LIMIT_COUNT) {
			map.put("captcha", true);
		}
		request.getSession().setAttribute(REDEEM_CODE_LIMIT,
				String.valueOf(new_limit));
	}
	
	/**
	 * 查询分享次数
	 * @param campaignNumber
	 * @param request
	 * @return
	 */
	private int queryShareTimes(String campaignNumber, HttpServletRequest request){
		int shareTimes = 0;
		// 获取分享次数(cookie),key对应PromotionCampaignAction类shareSuccess方法中的key
		String key = "shared_mark_" + campaignNumber;
		Cookie shareCookie = RequestUtil.getCookie(request, key);
		if (shareCookie != null) {
			String timesStr = shareCookie.getValue();
			int shareTimesFromCookie = timesStr.split(",").length;
			// 获取分享次数(数据库)
			PromotionCampaignShare shareInfo = shareService.findByCampaignNumber(campaignNumber);
			int shareTimesFromSql = shareInfo.getShareTimes();
			// 判断分享次数，如果cookie值大于数据库值，则以数据库值为准
			if (shareTimesFromCookie >= shareTimesFromSql) {
				shareTimes = shareTimesFromSql;
			} else {
				shareTimes = shareTimesFromCookie;
			}
		}
		return shareTimes;
	}
}
