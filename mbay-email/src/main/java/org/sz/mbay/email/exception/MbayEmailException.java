package org.sz.mbay.email.exception;

public class MbayEmailException extends Exception {
	private static final long serialVersionUID = -4817386460334501672L;

	public MbayEmailException(String message) {
		super(message);
	}

	public MbayEmailException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
