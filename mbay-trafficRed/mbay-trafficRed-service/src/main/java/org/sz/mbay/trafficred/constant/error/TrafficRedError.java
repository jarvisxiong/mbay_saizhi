package org.sz.mbay.trafficred.constant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

public final class TrafficRedError {
	
	public static final ErrorInfo NONE_EXIST_CAMPAIGN = new ErrorInfo(
			"NONE_EXIST_TRAFFICRED_CAMPAIGN", "找不到对应的活动！", null);
	
	public static final ErrorInfo SHARETEMPLATE_IMGUPLOD_FAIL = new ErrorInfo(
			"SHARETEMPLATE_IMGUPLOD_FAIL", "图片上传失败！", null);
	
	public static final ErrorInfo CAMPAIGN_CREATE_ERROR = new ErrorInfo(
			"CAMPAIGN_CREATE_ERROR", "活动创建失败,请检查信息是否填写完整!", null);
	
	public static final ErrorInfo RED_TRAFFIC_TRADE_ERROR = new ErrorInfo(
			"RED_TRAFFIC_TRADE_ERROR", "红包池交易失败，请检查红包池余量是否充足!", null);
	
	public static final ErrorInfo MBAY_TRAFFIC_TRADE_ERROR = new ErrorInfo(
			"MBAY_TRAFFIC_TRADE_ERROR", "美贝池交易失败，请检查美贝池余量是否充足!", null);
}
