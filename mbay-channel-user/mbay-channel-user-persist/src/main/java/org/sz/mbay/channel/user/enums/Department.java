package org.sz.mbay.channel.user.enums;

public enum Department {
	
	NON(""),OTHER("其它"), MARKET("市场部"), SALES("销售部"), PERSONNEL("人事部"),ADMINISTRATIVE("行政部"),TECHNOLOGY("技术部");
	
	private String value;
	
	private Department(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
