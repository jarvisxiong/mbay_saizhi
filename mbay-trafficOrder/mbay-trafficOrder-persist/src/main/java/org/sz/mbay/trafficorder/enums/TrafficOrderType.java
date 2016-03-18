package org.sz.mbay.trafficorder.enums;

public enum TrafficOrderType {
	
	DIRECT_RECHARGE("直充"),
	WECHAT_CAMPAIGN("微信伴侣活动"),
	PROMOTION_CAMPAIGN("促销神器活动"),
	CUSTOMER_SERVER("客户关怀"),
	APP_CAMPAIGN("APP诱惑"),
	TRAFFIC_RED("流量红包");
	
	private String value;
	
	TrafficOrderType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
