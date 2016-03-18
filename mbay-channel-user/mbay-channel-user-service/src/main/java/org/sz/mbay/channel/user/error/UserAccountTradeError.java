package org.sz.mbay.channel.user.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

public class UserAccountTradeError {
	
	public static final ErrorInfo USER_ACCOUNT_TRADE_ERROR = new ErrorInfo(
			"USER_ACCOUNT_TRADE_ERROR", "账户交易失败，请检查账户余额是否充足！");
			
	public static final ErrorInfo SMART2000_VERIFY_ERROR = new ErrorInfo(
			"SMART2000_VERIFY_ERROR", "加密狗验证失败", "/account/accountset.mbay");
			
}
