package org.sz.mbay.trafficred.result;

import org.apache.commons.lang.StringUtils;
import org.sz.mbay.base.wrap.ExecuteResult;

public class Result {
	
	private Boolean status;
	
	private String code;
	
	private String content;
	
	public Result() {}
	
	public Result(boolean status, String code, String content) {
		this.status = status;
		this.code = code;
		this.content = content;
	}
	
	public static Result create(boolean status, String code, String content) {
		return new Result(status, code, content);
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 转换成 ExecuteResult
	 * 
	 * @param result
	 * @return
	 */
	public ExecuteResult toExecuteResult() {
		ExecuteResult r = new ExecuteResult();
		r.setSuccess(this.getStatus());
		r.setError_code(this.getCode());
		r.setMessage(this.getContent());
		return r;
	}
	
	public boolean equals(String code) {
		if (this.getCode() == null || StringUtils.isEmpty(code)) {
			return false;
		}
		return this.getCode().equals(code);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this.getCode() == null || !(obj instanceof Result)) {
			return false;
		}
		Result o = (Result) obj;
		return this.getCode().equals(o.getCode());
	}
}
