package org.sz.mbay.trafficorder.enums;

public enum TrafficOrderStatus {

	PAYMENTS_DUE("未付款"), 
	RECHARGEING("已下发"), 
	RECHARGE_SUCCESS("已下发"), 
	RECHARGE_FAILED("充值失败"), 
	RECHARGE_CANCLED("交易关闭");
	
	private String value;

	private TrafficOrderStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
