package org.sz.mbay.channel.user.enums;

/**
 * @Description: 渠道商类别
 * @author han.han
 * @date 2014-8-27 上午11:44:18
 * 
 */
public enum ChannelProperty {
	
	NON(""), PERSONAL("个人"), ENTERPRISE("企业");
	
	private String value;
	
	private ChannelProperty(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
