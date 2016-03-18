package org.sz.mbay.base.exception.error;

public class ErrorInfo {
	
	private String code;
	
	private String url;
	
	private String message;
	
	private String errorPage;
	
	public ErrorInfo(String code, String message, String url, String errorPage) {
		this.code = code;
		this.message = message;
		this.url = url;
		this.errorPage = errorPage;
		ErrorRepository.errorRepo.put(code, this);
	}
	
	public ErrorInfo(String code, String message) {
		this.code = code;
		this.message = message;
		ErrorRepository.errorRepo.put(code, this);
	}
	
	public ErrorInfo(String code, String message, String url) {
		this.code = code;
		this.message = message;
		this.url = url;
		ErrorRepository.errorRepo.put(code, this);
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getErrorPage() {
		return errorPage;
	}
	
	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
	
}
