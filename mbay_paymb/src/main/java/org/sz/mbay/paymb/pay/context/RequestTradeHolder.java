package org.sz.mbay.paymb.pay.context;

import org.sz.mbay.base.web.utils.RequestHolder;
import org.sz.mbay.paymb.pay.annotation.TradePolicy;

public class RequestTradeHolder {

	private RequestTradeHolder() {
	}

	private static final String REQUEST_METHOD_KEY = "request_trade_policy_key";

	public static TradePolicy getRequesTradePolicy() {
		return (TradePolicy) RequestHolder.getServletRequest().getAttribute(REQUEST_METHOD_KEY);
	}

	public static void setRequesTradePolicy(TradePolicy tradePolicy) {
		RequestHolder.getServletRequest().setAttribute(REQUEST_METHOD_KEY, tradePolicy);
	}

}
