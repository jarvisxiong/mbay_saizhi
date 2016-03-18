package org.sz.mbay.trafficorder.exception;

public class TrafficOrderException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6174098527549334265L;

	public TrafficOrderException(String message, Throwable cause) {
		super(message, cause);

	}

	public TrafficOrderException(String message) {
		super(message);

	}

}
