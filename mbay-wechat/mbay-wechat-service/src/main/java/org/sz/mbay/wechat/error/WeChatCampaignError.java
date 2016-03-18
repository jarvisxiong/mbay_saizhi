package org.sz.mbay.wechat.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/**
 * @Description: 微信伴侣异常
 * @author frank.zong
 * @date 2015-1-13 上午9:48:07
 * 
 */
public final class WeChatCampaignError {
	/**
	 * 添加活动失败
	 */
	public static final ErrorInfo CAMPAIGN_ADD_ERROR = new ErrorInfo(
			"CAMPAIGN_ADD_ERROR", "添加活动失败", "/wechatCampaign/to_campaign_add.mbay");
	/**
	 * 您的账户余额不足于创建活动，请充值！
	 */
	public static final ErrorInfo CAMPAIGN_ADD_BALANCE_NOT_ENOUGH = new ErrorInfo(
			"CAMPAIGN_ADD_BALANCE_NOT_ENOUGH", "您的账户余额不足于创建活动，请充值！",
			"/wechatCampaign/to_campaign_add.mbay");
	
	/**
	 * 创建活动模式失败
	 */
	public static final ErrorInfo CAMPAIGN_MODEL_CREATE_ERROR = new ErrorInfo(
			"CAMPAIGN_MODEL_CREATE_ERROR", "创建活动模式失败",
			"/wechatCampaign/campaign_list.mbay");
	
	/**
	 * 创建模板失败
	 */
	public static final ErrorInfo CAMPAIGN_TEMPLATE_CREATE_ERROR = new ErrorInfo(
			"CAMPAIGN_TEMPLATE_CREATE_ERROR", "创建模板失败",
			"/wechatCampaign/campaign_list.mbay");
	
	/**
	 * 创建开发者模式失败
	 */
	public static final ErrorInfo CAMPAIGN_ADVANCED_CREATE_ERROR = new ErrorInfo(
			"CAMPAIGN_ADVANCED_CREATE_ERROR", "创建开发者模式失败",
			"/wechatCampaign/campaign_list.mbay");
	
	/**
	 * 活动编号不正确
	 */
	public static final ErrorInfo CAMPAIGN_NUMBER_ERROR = new ErrorInfo(
			"CAMPAIGN_NUMBER_ERROR", "活动编号不正确", "");
}