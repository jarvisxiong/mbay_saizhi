package org.sz.mbay.operator.enums;

public enum OperatorType {

	THREENETS("三网通用"), MOBILE("移动"), UNICOM("联通"), TELECOM("电信");

	private String value;

	private OperatorType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static OperatorType valueOf(int operator) {
		switch (operator) {
		case 0:
			return THREENETS;
		case 1:
			return MOBILE;
		case 2:
			return UNICOM;
		case 3:
			return TELECOM;
		default:
			return null;
		}
	}
}
