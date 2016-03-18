package org.sz.mbay.session.base.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 服务端返回值
 * 
 * @author jerry
 */
public class ResponseData implements Serializable {
	
	private static final long serialVersionUID = 6595841059749606375L;
	
	public static final ResponseData SUCCESS = new ResponseData(true);
	public static final ResponseData FAIL = new ResponseData(false);
	
	// 状态，true：成功，false：失败
	private Boolean status;
	
	public ResponseData() {
	}
	
	public ResponseData(boolean status) {
		this.status = status;
	}
	
	public static ResponseData create(boolean status) {
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
