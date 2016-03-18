package org.sz.mbay.trafficred.constant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/**
 * TrafficRedMobileAction errors
 * 
 * @author jerry
 */
public interface TrafficRedMobileError {
	
	public static final ErrorInfo CAMPAIGN_NUMBER_ERROR = new ErrorInfo(
			"CAMPAIGN_NUMBER_ERROR", "活动编号错误");
	
	public static final ErrorInfo CAMPAIGN_NOT_EXIST = new ErrorInfo(
			"CAMPAIGN_NOT_EXIST", "活动不存在");
	
	public static final ErrorInfo TEMPLATE_NOT_EXIST = new ErrorInfo(
			"TEMPLATE_NOT_EXIST", "活动模板不存在");
	
	public static final ErrorInfo SHAREINFO_NOT_EXIST = new ErrorInfo(
			"SHAREINFO_NOT_EXIST", "分享信息不存在");
	
	public static final ErrorInfo MBAY_GIFT_CONFIG_NOT_EXIST = new ErrorInfo(
			"MBAY_GIFT_CONFIG_NOT_EXIST", "送人分享信息不存在");
	
	public static final ErrorInfo TRAFFIC_SIZE_ERROR = new ErrorInfo(
			"TRAFFIC_SIZE_ERROR", "流量大小错误");
	
	public static final ErrorInfo COOKIE_NOT_SUPPORTTED = new ErrorInfo(
			"COOKIE_NOT_SUPPORTTED", "您的手机不支持cookie或cookie信息已被删除，请返回重新操作");
}
