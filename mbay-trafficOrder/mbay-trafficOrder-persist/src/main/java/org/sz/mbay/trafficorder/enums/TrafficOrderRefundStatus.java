package org.sz.mbay.trafficorder.enums;

/**
 * 流量充值订单退款
 * 
 * @author jerry
 */
public enum TrafficOrderRefundStatus {
	
	PROCESSING("处理中"),
	SUCCESS("退款成功"),
	FAIL("无法退款");
	
	private String value;
	
	private TrafficOrderRefundStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
