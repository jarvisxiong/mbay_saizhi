package org.sz.mbay.promotion.enums;

public enum CodeType {
	
	// 流量
	TRAFFIC("流量"),
	
	// MB
	MBAY("MB");
	
	private String value;
	
	private CodeType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
