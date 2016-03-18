package org.sz.mbay.channel.enums;

/**
 * 流量充值订单退款
 * 
 * @author jerry
 */
public enum TrafficChargeOrderRefundStatus {
	
	PROCESSING("处理中"),
	SUCCESS("退款成功"),
	FAIL("无法退款");
	
	private String value;
	
	private TrafficChargeOrderRefundStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
