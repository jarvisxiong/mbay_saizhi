package org.sz.mbay.trafficSign.enums;

public enum TrafficSignStatus {
	
	NOT_AUDIT("未审核"), AUDIT_SUCCESS("审核通过"), AUDIT_FAIL("审核不通过");
	
	private String value;
	
	private TrafficSignStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
