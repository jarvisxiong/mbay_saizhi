package org.sz.mbay.channel.costant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

public final class SMSPurchaseError {
	public static  final ErrorInfo NONE_EXIST_SMSPACKAGE=new ErrorInfo("NONE_EXIST_SMSPACKAGE","未找到对应的短信套餐，请重试！","/smspurchase/renderSMSPurchase.mbay");
	public static  final ErrorInfo SMS_PURSE_ERROR=new ErrorInfo("SMS_PURSE_ERROR","短信套餐购买失败，请重试！","/smspurchase/renderSMSPurchase.mbay");

}
