package org.sz.mbay.session.base.entity;

/**
 * 失败数据
 * 
 * @author jerry
 */
public class ResponseFailData extends ResponseData {
	
	private static final long serialVersionUID = -7509252225750600366L;
	
	// 失败时的错误信息
	private String errorMsg;
	
	// 错误码
	private String errorCode;
	
	public ResponseFailData() {
	}
	
	public ResponseFailData(String errorCode, String errorMsg) {
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
	public static ResponseData create(String errorMsg) {
		return new ResponseFailData(null, errorMsg);
	}
	
	/**
	 * 操作失败 + 错误描述 + 错误码
	 * 
	 * @param status
	 * @param errorMsg
	 */
	public static ResponseData create(String errorCode, String errorMsg) {
		return new ResponseFailData(errorCode, errorMsg);
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
