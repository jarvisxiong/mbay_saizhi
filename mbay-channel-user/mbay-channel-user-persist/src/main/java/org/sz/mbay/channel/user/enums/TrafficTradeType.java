package org.sz.mbay.channel.user.enums;

/**
 * @Description:
 * @author han.han
 * @date 2014-9-18 下午11:22:37
 * 
 */
public enum TrafficTradeType {
	
	NON("", ""),
	
	TRANSFER("转账", "100001"),
	
	TRAFFIC_RED("流量红包", "100002");
	
	private String value;
	// 编号
	private String number;
	
	private TrafficTradeType(String value, String number) {
		this.value = value;
		this.number = number;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
}
