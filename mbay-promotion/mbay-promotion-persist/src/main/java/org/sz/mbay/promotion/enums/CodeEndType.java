package org.sz.mbay.promotion.enums;

public enum CodeEndType {

	DATE("截止时间"), HOUR("截止小时");

	private String value;

	private CodeEndType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
