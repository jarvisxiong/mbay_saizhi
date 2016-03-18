package org.sz.mbay.channel.costant.error;

import org.sz.mbay.base.exception.error.ErrorInfo;

/**
 * @Description: 交易记录错误信息
 * @author han.han
 * @date 2015-1-20 下午10:30:06
 * 
 */
public class TradeRecordError {
	
	public static final ErrorInfo TRADE_RECORD_SEARCH_ERROR = new ErrorInfo("TRADE_RECORD_SEARCH_ERROR", "订单查询失败，请返回重试！", null);
}
