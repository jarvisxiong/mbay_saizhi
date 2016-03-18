package org.sz.mbay.remote.interfaces.wallet.base;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

/**
 * 接口返回值数据结构
 * 
 * @author jerry
 */
public class RIResponse {
	
	private boolean status;
	
	private String desc;
	
	private JSONObject data;
	
	private RIResponse() {
	}
	
	public static RIResponse from(String resp) {
		JSONObject obj = JSONObject.fromObject(resp);
		RIResponse data = new RIResponse();
		data.setStatus(obj.getBoolean("status"));
		data.setDesc(obj.getString("desc"));
		data.setData(obj.getJSONObject("data"));
		return data;
	}
	
	public String getErrorMsg() {
		if (!status) {
			return data.getString("msg");
		}
		return StringUtils.EMPTY;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public JSONObject getData() {
		return data;
	}
	
	public void setData(JSONObject data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
