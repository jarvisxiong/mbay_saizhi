package org.sz.mbay.captcha;

/**
 * 方法执行结果辅助类 
 */
public class CaptchaResult {
	
	public static final CaptchaResult successExecute=new CaptchaResult(true,"");
	public static final CaptchaResult failExecute=new CaptchaResult(false,"");
	
	private String error_code;
	private boolean success;
	private String message;
	
	public CaptchaResult() {
		
	}
	
	public CaptchaResult(boolean success) {
		this.success = success;
	}
	
	/**
	 * @param success 执行结果
	 * @param message 结果描述
	 */
	public CaptchaResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	/**
	 * @param success 执行结果
	 * @param error_code 结果代码码
	 * @param message 结果描述
	 */
	public CaptchaResult(boolean success, String error_code, String message) {
		this.success = success;
		this.error_code = error_code;
		this.message = message;
	}
	
	public String getError_code() {
		return error_code;
	}
	
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
