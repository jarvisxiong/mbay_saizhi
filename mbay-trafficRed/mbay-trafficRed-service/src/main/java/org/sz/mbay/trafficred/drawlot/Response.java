package org.sz.mbay.trafficred.drawlot;

public abstract class Response {
	
	// 成功/失败
	private Boolean status;
	
	// 结果码
	private String code;
	
	// 描述
	private String content;
	
	public Response() {}
	
	public Response(Boolean status, String code, String content) {
		this.status = status;
		this.code = code;
		this.content = content;
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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Response)) {
			return false;
		}
		Response o = (Response) obj;
		return this.getCode().equals(o.getCode());
	}
}
