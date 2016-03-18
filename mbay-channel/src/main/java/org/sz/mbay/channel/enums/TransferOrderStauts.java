package org.sz.mbay.channel.enums;

public enum TransferOrderStauts {
	NOPAYMENT("未付款"),
	SUCCESS("转账成功");
	
	private String value;
	
	private TransferOrderStauts(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
