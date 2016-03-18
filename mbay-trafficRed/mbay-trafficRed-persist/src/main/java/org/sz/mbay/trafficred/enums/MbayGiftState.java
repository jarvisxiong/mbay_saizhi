package org.sz.mbay.trafficred.enums;

/**
 * MB送人记录状态
 * 
 * @author jerry
 */
public enum MbayGiftState {
	
	NON(""), 
	CREATED("已创建，账户已扣款"),
	RECIEVED("已被领取");
	
	private String value;
	
	private MbayGiftState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
