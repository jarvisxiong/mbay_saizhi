package org.sz.mbay.channel.enums;

/**
 * 邮件模板类型
 * 
 * @author jerry
 */
public enum EmailTemplateType {
	
	NON(""),
	CERTIFICATE_SUCCESS("实名认证通过"),
	RESET_PASSWORD("点击链接找回密码"),
	TRANSFER_NOTICE("转账"),
	TRANSFER_PRESENT("转账赠送");
	
	private String value;
	
	private EmailTemplateType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
