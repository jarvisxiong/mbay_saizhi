package org.sz.mbay.trafficred.enums;

public enum ProductType {
	
	TRAFFIC_PACKAGE("美贝"), MBAY_PACKAGE("MB");
	
	ProductType(String value) {
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
