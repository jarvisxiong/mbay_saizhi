package org.sz.mbay.channel.user.enums;

public enum PaymentType {
	
	NON(""), INCOME("收入"), EXPENSE("支出");

	private String value;
	
	private PaymentType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
