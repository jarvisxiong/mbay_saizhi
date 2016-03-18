package org.sz.mbay.channel.user.enums;

public enum EnterpriseProperty {
	
	NON(""),
	GOVERNMENT("政府机关/事业单位"), 
	STATERUN("国营"), 
	PRIVATE("私营"), 
	SINOFOREIGN("中外合资"), 
	FOREIGN("外资"), 
	OTHER("其他");
	
	private String value;
	
	private EnterpriseProperty(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
