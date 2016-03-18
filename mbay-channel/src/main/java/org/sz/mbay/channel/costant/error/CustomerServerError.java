package org.sz.mbay.channel.costant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

public class CustomerServerError {
	
	public static final ErrorInfo CUSTOMER_TRADE_ERROR = new ErrorInfo("CUSTOMER_TRADE_ERROR", "账户交易失败，请重试！", null);
	
	public static final ErrorInfo CUSTOMER_REMAIN_ERROR = new ErrorInfo("CUSTOMER_REMAIN_ERROR", "您的账户余额不足，请充值！", null);
	
	public static final ErrorInfo CUSTOMER_ORDER_ERROR = new ErrorInfo("CUSTOMER_ORDER_ERROR", "订单创建失败，请重试！", null);
	public static final ErrorInfo NONE_EXIST_BATCHCHARGE = new ErrorInfo("NONE_EXIST_BATCHCHARGE", "不存在批充信息！", null);
}
