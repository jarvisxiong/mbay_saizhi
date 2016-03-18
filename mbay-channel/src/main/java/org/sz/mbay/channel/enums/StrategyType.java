package org.sz.mbay.channel.enums;

public enum StrategyType {
	
	NON("未知"), CODE("兑换码"), WECHAT("微信");
	
	private String value;
	
	private StrategyType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
