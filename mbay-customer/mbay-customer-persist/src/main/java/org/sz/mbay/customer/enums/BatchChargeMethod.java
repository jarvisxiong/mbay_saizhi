package org.sz.mbay.customer.enums;

public enum BatchChargeMethod {
	
	NON(""), PERIOD_CHARGE("定期充值"), HAND_CHARGE("手动充值");
	
	private String value;
	
	private BatchChargeMethod(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
