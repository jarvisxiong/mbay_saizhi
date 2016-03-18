package org.sz.mbay.channel.user.exception;

public class RedAccountTradeException extends Exception {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5761933449818617658L;

	public RedAccountTradeException() {
	}
	
	public RedAccountTradeException(String message) {
		super(message);
	}
	
	public RedAccountTradeException(Throwable cause) {
		super(cause);
	}
	
	public RedAccountTradeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RedAccountTradeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
