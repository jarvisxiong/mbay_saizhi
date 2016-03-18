package org.sz.mbay.promotion.enums;

public enum LimitType {
	
	DAY("每日"), WEEK("每周"), MONTH("每月"), ACTIVE("活动期间"), UNLIMITED("不限");
	
	private String value;
	
	private LimitType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
