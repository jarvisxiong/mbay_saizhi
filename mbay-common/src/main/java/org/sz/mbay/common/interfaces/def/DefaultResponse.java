package org.sz.mbay.common.interfaces.def;

import org.sz.mbay.common.interfaces.Response;

/**
 * 默认接口返回值数据结构
 * 
 * @author jerry
 */
public class DefaultResponse extends Response {
	
	// 请求状态码，对应http响应状态码，200为成功，其他失败
	private String resultCode;
	
	// 描述信息
	private String desc;
	
	// 请求结果
	private Object result;
	
	// 错误编码，没有错误或请求不成功（如404）时为0，其他需要用户定义
	private String errorCode;
	
	public String getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Object getResult() {
		return result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * 根据响应码生成对象
	 * 
	 * @param statusCode
	 * @return
	 */
	public static Response fromResultCode(String resultCode) {
		DefaultResponse data = new DefaultResponse();
		data.setResultCode(resultCode);
		data.setDesc(ResultCode.getDesc(resultCode));
		data.setErrorCode(ErrorCode.SUCCESS_CODE.getCode());
		return data;
	}
	
	/**
	 * 根据返回数据生成对象
	 * 
	 * @param statusCode
	 * @return
	 */
	public static Response fromResult(Object result) {
		DefaultResponse data = new DefaultResponse();
		data.setResultCode(ResultCode.SUCCESS_CODE.getCode());
		data.setDesc(ResultCode.SUCCESS_CODE.getDesc());
		data.setErrorCode(ErrorCode.SUCCESS_CODE.getCode());
		data.setResult(result);
		return data;
	}
	
	/**
	 * 根据错误码生成对象
	 * 
	 * @param statusCode
	 * @return
	 */
	public static Response fromErrorCode(String module, String errorCode) {
		DefaultResponse data = new DefaultResponse();
		data.setResultCode(ResultCode.SUCCESS_CODE.getCode());
		data.setErrorCode(errorCode);
		data.setDesc(ErrorCode.getDesc(module, errorCode));
		return data;
	}
	
	@Override
	public boolean isSuccess() {
		return resultCode.equals(ResultCode.SUCCESS_CODE.getCode())
				&& errorCode.equals(ErrorCode.SUCCESS_CODE.getCode());
	}
	
}
