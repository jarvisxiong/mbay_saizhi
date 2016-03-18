package org.sz.mbay.channel.enums;

public enum CodeType {
	
	// 表示有活动生成的兑换码
	ORIGINAL("原始"),
	
	// 表示当月多次参与无法重发，而生成的兑换码
	RESEND("系统");
	
	private String value;
	
	private CodeType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
