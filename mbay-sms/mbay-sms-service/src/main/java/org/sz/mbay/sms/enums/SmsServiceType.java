package org.sz.mbay.sms.enums;

public enum SmsServiceType {
	
	NULL(""),
	
	SDK("SDK短信服务平台"),
	
	YZX("云之讯短信服务平台"),
	
	@Deprecated HHM("何辉美短信服务平台");
	
	private String value;
	
	SmsServiceType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
