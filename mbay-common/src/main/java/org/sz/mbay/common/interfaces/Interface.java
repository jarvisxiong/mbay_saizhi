package org.sz.mbay.common.interfaces;

import java.util.Map;

import org.sz.mbay.common.util.HttpSupport.ReqMethod;

/**
 * 接口信息，地址及参数
 * 
 * @author jerry
 */
public abstract class Interface {
	
	/** 接口名称 */
	protected String name;
	
	/** 接口完整地址 */
	protected String url;
	
	/** 接口请求方式 */
	protected ReqMethod reqMethod;
	
	/** 请求头 */
	protected Map<String, String> headers;
	
	/**
	 * 请求地址预处理（如参数填补，加密等）
	 * 返回处理后的接口对象
	 */
	public abstract Interface preproccess(Object paramsList)
			throws ParamsPreprocessException;
			
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public ReqMethod getReqMethod() {
		return reqMethod;
	}
	
	public void setReqMethod(ReqMethod reqMethod) {
		this.reqMethod = reqMethod;
	}
	
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
