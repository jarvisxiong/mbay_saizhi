package org.sz.mbay.channel.enums;

public enum OrderStatus {
	NON(""), CREATED("已创建"), CANCER("已取消"), FINISHED("已完成");
	
	private String value;
	
	OrderStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
