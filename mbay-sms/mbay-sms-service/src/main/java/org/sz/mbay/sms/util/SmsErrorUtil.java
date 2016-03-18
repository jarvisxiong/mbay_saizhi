package org.sz.mbay.sms.util;

public class SmsErrorUtil {
    public static String getHHMErrorInfo(String code) {

	String errorInfo = null;
	switch (code) {
	case "101":
	    errorInfo = "无此用户";
	    break;
	case "102":
	    errorInfo = "密码错";
	    break;
	case "103":
	    errorInfo = "提交过快（提交速度超过流速限制）";
	    break;
	case "104":
	    errorInfo = "系统忙（因平台侧原因，暂时无法处理提交的短信）";
	    break;
	case "105":
	    errorInfo = "敏感短信（短信内容包含敏感词）";
	    break;
	case "106":
	    errorInfo = "消息长度错（>536或<=0）";
	    break;
	case "107":
	    errorInfo = "包含错误的手机号码";
	    break;
	case "108":
	    errorInfo = "手机号码个数错（群发>50000或<=0;单发>200或<=0）";
	    break;
	case "109":
	    errorInfo = "无发送额度（该用户可用短信数已使用完）";
	    break;
	case "110":
	    errorInfo = "不在发送时间内";
	    break;
	case "111":
	    errorInfo = "超出该账户当月发送额度限制";
	    break;
	case "112":
	    errorInfo = "无此产品，用户没有订购该产品";
	    break;
	case "113":
	    errorInfo = "extno格式错（非数字或者长度不对）";
	    break;
	case "115":
	    errorInfo = "自动审核驳回";
	    break;
	case "116":
	    errorInfo = "签名不合法，未带签名（用户必须带签名的前提下）";
	    break;
	case "117":
	    errorInfo = "IP地址认证错,请求调用的IP地址不是系统登记的IP地址";
	    break;
	case "118":
	    errorInfo = "用户没有相应的发送权限";
	    break;
	case "119":
	    errorInfo = "用户已过期";
	    break;
	case "120":
	    errorInfo = "发送内容不符合报备的白短信模板";
	    break;
	default:
	    break;
	}

	return errorInfo;
    }
}
