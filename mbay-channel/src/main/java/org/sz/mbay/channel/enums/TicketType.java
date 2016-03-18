package org.sz.mbay.channel.enums;

/**
 * 券包种类
 * 
 * @author jerry
 */
public enum TicketType {
	
	NON(""),
	WECHAT_CAMPAIGN("微信伴侣活动");
	
	private String value;
	
	private TicketType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
