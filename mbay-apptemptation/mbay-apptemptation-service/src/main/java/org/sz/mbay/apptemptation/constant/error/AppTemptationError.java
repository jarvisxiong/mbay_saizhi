package org.sz.mbay.apptemptation.constant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/**
 * app诱惑异常
 * 
 * @author jerry
 */
public final class AppTemptationError {
	
	/**
	 * 添加活动失败
	 */
	public static final ErrorInfo CAMPAIGN_ADD_ERROR = new ErrorInfo(
			"CAMPAIGN_ADD_ERROR", "添加活动失败", "/app_temptation/to_campaign_add.mbay");
	
	/**
	 * 您的账户余额不足于创建活动，请充值！
	 */
	public static final ErrorInfo CAMPAIGN_ADD_BALANCE_NOT_ENOUGH = new ErrorInfo(
			"CAMPAIGN_ADD_BALANCE_NOT_ENOUGH", "您的账户余额不足于创建活动，请充值！",
			"/app_temptation/to_campaign_add.mbay");
	
	/**
	 * 创建模板失败
	 */
	public static final ErrorInfo CAMPAIGN_TEMPLATE_CREATE_ERROR = new ErrorInfo(
			"CAMPAIGN_TEMPLATE_CREATE_ERROR", "创建模板失败",
			"/app_temptation/campaign_list.mbay");
	
	/**
	 * 活动编号不正确
	 */
	public static final ErrorInfo CAMPAIGN_NUMBER_ERROR = new ErrorInfo(
			"CAMPAIGN_NUMBER_ERROR", "活动编号不正确", "");
	
	/**
	 * 订单号错误
	 */
	public static final ErrorInfo ORDER_NUMBER_ERROR = new ErrorInfo(
			"ORDER_NUMBER_ERROR", "订单编号不正确", "");
	
	/**
	 * 非法退款申请
	 */
	public static final ErrorInfo ILLEGAL_REFUND_ERROR = new ErrorInfo(
			"ORDER_NUMBER_ERROR", "非法退款申请", "");
}
