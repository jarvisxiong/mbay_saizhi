package org.sz.mbay.trafficred.constant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

public class TrafficRedAdvancedError {
	
	public static final ErrorInfo ILLEGAL_ARGUMENT = new ErrorInfo(
			"ILLEGAL_ARGUMENT", "输入参数有错误");
	
	public static final ErrorInfo SELLER_OR_CAMPAIGN_NOT_EXIST = new ErrorInfo(
			"SELLER_OR_CAMPAIGN_NOT_EXIST", "商家或活动不存在");
	
	public static final ErrorInfo REQUEST_TIMEOUT = new ErrorInfo(
			"REQUEST_TIMEOUT", "请求超时");
	
	public static final ErrorInfo DISABLED_ADVANCED = new ErrorInfo(
			"DISABLED_ADVANCED", "开发者模式未启用");
	
	public static final ErrorInfo ILLEGAL_SIGN = new ErrorInfo(
			"ILLEGAL_SIGN", "签名不正确");
}
