package org.sz.mbay.trafficred.enums;

/**
 * 流量红包流程状态
 * 
 * @author jerry
 */
public enum PackageState {
	
	CREATED("已下发"), RECIEVED("已接收");
	
	private PackageState(String value) {
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
