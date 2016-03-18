package org.sz.mbay.channel.user.exception;

public class UserAccountTradeException extends Exception {
	
	private static final long serialVersionUID = 1997753363232807009L;
	
	public UserAccountTradeException() {
	}
	
	public UserAccountTradeException(String message) {
		super(message);
	}
	
	public UserAccountTradeException(Throwable cause) {
		super(cause);
	}
	
	public UserAccountTradeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UserAccountTradeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
