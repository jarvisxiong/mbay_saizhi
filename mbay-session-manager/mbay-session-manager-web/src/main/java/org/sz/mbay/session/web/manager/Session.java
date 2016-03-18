package org.sz.mbay.session.web.manager;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Session {
	
	// sesisonid
	private final String id;
	
	// 过期时间
	private final int timeout;
	
	// 数据
	private Map<String, Serializable> data = new ConcurrentHashMap<String, Serializable>();
	
	public Session(final String id, final int timeout) {
		this.id = id;
		this.timeout = timeout;
	}
	
	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Serializable value) {
		data.put(key, value);
	}
	
	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public Serializable getAttribute(String key) {
		return data.get(key);
	}
	
	/**
	 * 删除数据
	 * 
	 * @param key
	 */
	public void removeAttribute(String key) {
		data.remove(key);
	}
	
	public String getId() {
		return id;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
}
