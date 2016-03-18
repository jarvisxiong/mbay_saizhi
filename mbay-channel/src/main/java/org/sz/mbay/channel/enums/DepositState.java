package org.sz.mbay.channel.enums;

public enum DepositState {
	
	NON(""), PAYMENTS_DUE("未付款"), SUCCESS("成功");
	
	private String value;
	
	private DepositState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
