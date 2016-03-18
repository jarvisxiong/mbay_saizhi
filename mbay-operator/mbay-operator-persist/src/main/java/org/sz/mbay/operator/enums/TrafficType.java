package org.sz.mbay.operator.enums;

public enum TrafficType {
	
	NON(""), STATE("全国流量"), PROVINCE("省内流量");
	
	public String value;
	
	private TrafficType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
