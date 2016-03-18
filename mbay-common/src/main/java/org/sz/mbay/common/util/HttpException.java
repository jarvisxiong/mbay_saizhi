package org.sz.mbay.common.util;

/**
 * 自定义http异常
 * 
 * @author jerry
 */
public class HttpException extends Exception {
	
	private static final long serialVersionUID = -1090206993311739114L;
	
	// 请求状态码
	private String statusCode;
	
	public HttpException(String errorMsg, String statusCode) {
		super(errorMsg);
		this.statusCode = statusCode;
	}
	
	public HttpException(String errorMsg) {
		super(errorMsg);
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
