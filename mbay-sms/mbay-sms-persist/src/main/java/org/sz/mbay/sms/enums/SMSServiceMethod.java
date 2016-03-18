package org.sz.mbay.sms.enums;

/**
 * @Description: 短信接口实现方式
 * @author frank.zong
 * @date 2014-11-10 下午10:51:49
 */
public enum SMSServiceMethod {
	
	@Deprecated HHMSMSServiceImpl("何辉美"),
	
	SDKSMSServiceImpl("SDK"),
	
	YZXSMSServiceImpl("云之讯");
	
	private String value;
	
	private SMSServiceMethod(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
