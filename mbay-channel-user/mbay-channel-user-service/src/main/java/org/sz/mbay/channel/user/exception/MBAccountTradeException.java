package org.sz.mbay.channel.user.exception;

public class MBAccountTradeException extends Exception {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7180094811530868723L;

	public MBAccountTradeException() {
	}
	
	public MBAccountTradeException(String message) {
		super(message);
	}
	
	public MBAccountTradeException(Throwable cause) {
		super(cause);
	}
	
	public MBAccountTradeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MBAccountTradeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
