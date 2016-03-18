package org.sz.mbay.base.enums;

public enum SendWay {
	
	DEFAULT("流量+MB"), TRAFFIC("流量"), MBAY("MB");
	
	private String value;
	
	private SendWay(String value) {
		this.value = value;
	}
	
	public String getValue() { 
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
