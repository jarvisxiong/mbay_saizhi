package org.sz.mbay.base.enums;

/**
 * 活动状态
 * 
 * @author ONECITY
 * 
 */
public enum EventType {
	
	NON(""), WECHAT("微信营销"), REDEEM_PROMOTION("促销神器");
	
	private String value;
	
	EventType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
