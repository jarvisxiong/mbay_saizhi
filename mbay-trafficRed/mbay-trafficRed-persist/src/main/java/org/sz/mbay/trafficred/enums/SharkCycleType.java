package org.sz.mbay.trafficred.enums;

public enum SharkCycleType {
	NO_LIMITED("不限制"), TOTAL("总共"), DAY("天"), WEEK("周"), MONTH("月");
	
	private String value;
	
	private SharkCycleType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
