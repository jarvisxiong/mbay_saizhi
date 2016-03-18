package org.sz.mbay.channel.enums;

public enum StoreCampaginTradeType {
	NON(""), DELIVER("发放扣除"), REDEEM("兑换扣除");

	private String value;

	StoreCampaginTradeType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
