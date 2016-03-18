package org.sz.mbay.channel.exception;

import java.io.Serializable;

import org.sz.mbay.base.exception.error.ErrorInfo;

@SuppressWarnings("serial")
public class WebInterfaceException extends RuntimeException implements Serializable {
	
	private ErrorInfo errorInfo;
	
	public WebInterfaceException(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}
	
	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
