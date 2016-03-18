package org.sz.mbay.promotion.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/**
 * @Description: 促销神器异常
 * @author frank.zong
 * @date 2014-10-25 上午12:48:07
 * 		
 */
public final class PromotionCampaignError {
	
	/**
	 * 添加促销活动失败
	 */
	public static final ErrorInfo CAMPAIGN_ADD_ERROR = new ErrorInfo(
			"CAMPAIGN_ADD_ERROR", "添加促销活动失败！",
			"/promotionCampaign/to_campaign_add.mbay");
			
	/**
	 * 您的账户余额不足于创建活动，请充值！
	 */
	public static final ErrorInfo CAMPAIGN_ADD_BALANCE_NOT_ENOUGH = new ErrorInfo(
			"CAMPAIGN_ADD_BALANCE_NOT_ENOUGH", "您的账户余额不足于创建活动，请充值！",
			"/promotionCampaign/to_campaign_add.mbay");
			
	/**
	 * 活动编号不正确
	 */
	public static final ErrorInfo CAMPAIGN_NUMBER_ERROR = new ErrorInfo(
			"CAMPAIGN_NUMBER_ERROR", "活动编号不正确！",
			"/promotionCampaign/campaign_list.mbay");
	/**
	 * 取消活动失败
	 */
	public static final ErrorInfo CANCEL_CAMPAIGN_ERROR = new ErrorInfo(
			"CANCEL_CAMPAIGN_ERROR", "取消活动失败！",
			"/promotionCampaign/campaign_list.mbay");
	/**
	 * 活动策略编号不正确
	 */
	public static final ErrorInfo CAMPAIGN_STRATEGY_ERROR = new ErrorInfo(
			"CAMPAIGN_STRATEGY_ERROR", "活动策略编号不正确！",
			"/promotionCampaign/campaign_list.mbay");
	/**
	 * 编辑模板失败
	 */
	public static final ErrorInfo CAMPAIGN_TEMPLATE_ERROR = new ErrorInfo(
			"REDEEM_TEMPLATE_EDITERROR", "编辑模板失败！",
			"/promotionCampaign/campaign_list.mbay");
			
	/**
	 * 您的账户余额不足于创建活动，请充值！
	 */
	public static final ErrorInfo BALANCE_NOT_ENOUGH = new ErrorInfo(
			"PROMOTION_ADD_BALANCE_NOT_ENOUGH", "您的账户余额不足于创建活动，请充值！",
			"/promotionCampaign/to_campaign_add.mbay");
			
	/**
	 * 红包池交易失败，请检查红包池余量是否充足！
	 */
	public static final ErrorInfo TRAFFIC_TRADE_ERROR = new ErrorInfo(
			"TRAFFIC_TRADE_ERROR", "红包池交易失败，请检查红包池余量是否充足!",
			"/promotionCampaign/to_campaign_add.mbay");
			
	/**
	 * 美贝池交易失败，请检查美贝池余量是否充足
	 */
	public static final ErrorInfo MBAY_TRADE_ERROR = new ErrorInfo(
			"MBAY_TRADE_ERROR", "美贝池交易失败，请检查美贝池余量是否充足!",
			"/promotionCampaign/to_campaign_add.mbay");
			
}
