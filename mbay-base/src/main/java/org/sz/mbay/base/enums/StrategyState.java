package org.sz.mbay.base.enums;

/**
 * 策略状态
 * 
 * @author ONECITY
 * 
 */
public enum StrategyState {
	
	NON(""), ENABLE("启用"), DISABL("禁用");
	
	private String value;
	
	private StrategyState(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
