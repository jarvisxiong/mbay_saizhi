package org.sz.mbay.rocketmq.helper;

/**
 * 主题类型
 * 
 * @author jerry
 */
public enum TopicType {
	
	TRAFFIC_ORDER("流量订单");
	
	private TopicType(String value) {
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
