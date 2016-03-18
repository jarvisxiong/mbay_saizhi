package org.sz.mbay.promotion.enums;

public enum Model {
	
	PLAY("再玩一次"), GET("再领一次");
	
	private String value;
	
	private Model(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
