package org.sz.mbay.base.wrap;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 服务端返回值
 * 
 * @author jerry
 */
public class Response implements Serializable {
	
	private static final long serialVersionUID = 6595841059749606375L;
	
	public static final Response SUCCESS = new Response(true);
	public static final Response FAIL = new Response(false);
	
	// 状态，true：成功，false：失败
	private Boolean status;
	
	public Response() {}
	
	public Response(boolean status) {
		this.status = status;
	}
	
	public static Response create(boolean status) {
		return status ? SUCCESS : FAIL;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
