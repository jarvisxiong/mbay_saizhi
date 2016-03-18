package org.sz.mbay.common.interfaces;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 接口请求返回值
 * 
 * @author jerry
 */
public abstract class Response {
	
	// json配置
	private static final JsonConfig JSON_CONF = new JsonConfig();
	
	static {
		JSON_CONF.setExcludes(new String[] { "success" });
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this, JSON_CONF).toString();
	}
	
	/**
	 * 返回当前响应是否成功
	 * 
	 * @return
	 */
	public abstract boolean isSuccess();
	
}
