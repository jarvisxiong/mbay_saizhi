package org.sz.mbay.trafficrecharge.exception;

public class TrafficRechargeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6174098527549334265L;

	public TrafficRechargeException(String message, Throwable cause) {
		super(message, cause);

	}

	public TrafficRechargeException(String message) {
		super(message);

	}

}
