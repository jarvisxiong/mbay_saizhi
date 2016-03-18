package org.sz.mbay.paymb.pay.exception;

import org.sz.mbay.paymb.pay.response.TradeResponse;

public class MBTradeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5129040912364572444L;
	private TradeResponse tradeResponse;

	public TradeResponse getTradeResponse() {
		return tradeResponse;
	}

	public MBTradeException(TradeResponse response) {
		this.tradeResponse = response;
	}

}
