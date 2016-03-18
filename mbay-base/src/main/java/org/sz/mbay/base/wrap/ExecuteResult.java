package org.sz.mbay.base.wrap;

/**
 * 方法执行结果辅助类 
 */
public class ExecuteResult {
	
	public static final ExecuteResult successExecute=new ExecuteResult(true,"执行成功");
	public static final ExecuteResult failExecute=new ExecuteResult(false,"执行失败");
	
	private String error_code;
	private boolean success;
	private String message;
	
	public ExecuteResult() {
		
	}
	
	public ExecuteResult(boolean success) {
		this.success = success;
	}
	
	/**
	 * @param success 执行结果
	 * @param message 结果描述
	 */
	public ExecuteResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	/**
	 * @param success 执行结果
	 * @param error_code 结果代码码
	 * @param message 结果描述
	 */
	public ExecuteResult(boolean success, String error_code, String message) {
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
