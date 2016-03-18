package org.sz.mbay.wallet.constant.error;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

/**
 * 交易错误
 * 
 * @author jerry
 */
public class TradeRecordError {
	
	public static Response UNKNOWN_TRADE_TYPE = ResponseFail.create(
			"UNKNOWN_TRADE_TYPE", "未识别的交易类型");
}
