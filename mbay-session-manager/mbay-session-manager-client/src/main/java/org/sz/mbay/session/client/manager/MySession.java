package org.sz.mbay.session.client.manager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.common.util.HttpSupport;
import org.sz.mbay.common.util.HttpSupport.ReqMethod;
import org.sz.mbay.session.base.entity.ResponseData;
import org.sz.mbay.session.base.entity.ResponseFailData;
import org.sz.mbay.session.base.entity.ResponseSucData;
import org.sz.mbay.session.client.config.ClientConfig;
import org.sz.mbay.session.client.context.ClientContext;
import org.sz.mbay.session.client.enums.Field;
import org.sz.mbay.session.client.util.SerializeUtil;

public class MySession {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MySession.class);
	
	private static final String SERVER = ClientConfig.getProperty(ClientConfig.SERVER);
	
	// 临时配置项，会覆盖对应的配置文件相应项
	// 且会在一次setAttribute方法执行后被清空
	private Map<Field, String> fields;
	
	private MySession() {
	}
	
	public static MySession getInstance() {
		return new MySession();
	}
	
	/**
	 * 设置临时配置
	 * 
	 * @param field
	 * @param value
	 */
	public void setField(Field field, String value) {
		if (fields == null) {
			synchronized (this) {
				if (fields == null) {
					fields = new HashMap<Field, String>();
				}
			}
		}
		fields.put(field, value);
	}
	
	/**
	 * 获取临时配置
	 * 
	 * @param field
	 * @return
	 */
	public String getField(Field field) {
		if (field != null) {
			return fields.get(field);
		}
		return null;
	}
	
	/**
	 * 删除临时变量
	 * 
	 * @param field
	 */
	public void removeField(Field field) {
		if (field != null) {
			fields.remove(field);
		}
	}
	
	/**
	 * 清空临时配置
	 */
	public void clearFields() {
		if (fields != null) {
			fields.clear();
		}
	}
	
	/**
	 * 设置session属性
	 * 
	 * @param key
	 * @param data
	 * @throws Exception
	 */
	public boolean setAttribute(String key, Serializable data) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("jsessionid", getConfigValue(Field.JSESSIONID));
			params.put("prefix", getConfigValue(Field.PREFIX));
			params.put("timeout", getConfigValue(Field.TIMEOUT));
			params.put("key", key);
			params.put("data", SerializeUtil.serialize(data));
			
			String url = SERVER + "/session/attr/set.mbay";
			String resp = HttpSupport.connect(url, params);
			return formatHttpResult(resp).getStatus();
		} catch (Exception e) {
			LOGGER.error("MySession setAttribute:" + e.getMessage());
			return false;
		}
	}
	
	/**
	 * 获取session属性
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Serializable getAttribute(String key) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("jsessionid", getConfigValue(Field.JSESSIONID));
			params.put("prefix", getConfigValue(Field.PREFIX));
			params.put("key", key);
			
			String url = SERVER + "/session/attr/get.mbay";
			String resp = HttpSupport.connect(url, ReqMethod.GET, params);
			
			Object data = ((ResponseSucData) formatHttpResult(resp)).getData();
			if (data != null) {
				return SerializeUtil.deserialize((String) data);
			}
		} catch (Exception e) {
			LOGGER.error("MySession getAttribute:" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 删除session属性
	 * 
	 * @param key
	 * @return
	 */
	public boolean removeAttribute(String key) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("jsessionid", getConfigValue(Field.JSESSIONID));
			params.put("prefix", getConfigValue(Field.PREFIX));
			params.put("key", key);
			
			String url = SERVER + "/session/attr/remove.mbay";
			String resp = HttpSupport.connect(url, ReqMethod.GET, params);
			return formatHttpResult(resp).getStatus();
		} catch (Exception e) {
			LOGGER.error("MySession removeAttribute:" + e.getMessage());
			return false;
		}
	}
	
	/*----------------------------------------------
	 *                  私有方法
	 *--------------------------------------------*/
	
	/**
	 * 获取最终配置参数，优先使用临时参数
	 * 
	 * @param name
	 * @return
	 */
	private String getConfigValue(Field name) {
		if (fields != null && fields.get(name) != null) {
			return (String) fields.get(name);
		} else {
			switch (name) {
				case PREFIX:
					return ClientConfig.getProperty(ClientConfig.KEY_PREFIX, "");
				case TIMEOUT:
					return ClientConfig.getProperty(ClientConfig.TIMEOUT, "30");
				case JSESSIONID:
					return ClientContext.getHttpSession().getId();
				default:
					return null;
			}
		}
	}
	
	/**
	 * 格式化返回值
	 * 
	 * @param resp
	 * @return
	 */
	private ResponseData formatHttpResult(String resp) throws Exception {
		JSONObject jobj = JSONObject.fromObject(resp);
		if (jobj.getBoolean("status")) {
			return (ResponseData) JSONObject.toBean(jobj, ResponseSucData.class);
		} else {
			ResponseFailData fail = (ResponseFailData)
					JSONObject.toBean(jobj, ResponseFailData.class);
			throw new Exception(fail.getErrorCode() + "->" + fail.getErrorMsg());
		}
	}
	
}
