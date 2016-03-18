package org.sz.mbay.promotion.enums;

public enum RedeemCodeMethod {

	AUTO("自动"), MANUAL("手动");

	private String value;

	private RedeemCodeMethod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
