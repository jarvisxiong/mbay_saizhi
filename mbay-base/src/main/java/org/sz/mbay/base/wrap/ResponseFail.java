package org.sz.mbay.base.wrap;

/**
 * 失败数据
 * 
 * @author jerry
 */
public class ResponseFail extends Response {
	
	private static final long serialVersionUID = -7509252225750600366L;
	
	// 失败时的错误信息
	private String errorMsg;
	
	// 错误码
	private String errorCode;
	
	public ResponseFail() {}
	
	public ResponseFail(String errorCode, String errorMsg) {
		super(false);
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}
	
	/**
	 * 操作失败 + 错误描述
	 * 
	 * @param status
	 * @param errorMsg
	 */
	public static Response create(String errorMsg) {
		return new ResponseFail(null, errorMsg);
	}
	
	/**
	 * 操作失败 + 错误描述 + 错误码
	 * 
	 * @param status
	 * @param errorMsg
	 */
	public static Response create(String errorCode, String errorMsg) {
		return new ResponseFail(errorCode, errorMsg);
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
