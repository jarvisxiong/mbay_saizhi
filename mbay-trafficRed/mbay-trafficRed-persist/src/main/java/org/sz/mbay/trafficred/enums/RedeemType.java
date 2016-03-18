package org.sz.mbay.trafficred.enums;

/**
 * 兑换类别
 *
 * @author jerry
 */
public enum RedeemType {
	
	NON(""), TRAFFIC("流量"), MBAY("MB");
	
	private String value;
	
	private RedeemType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
