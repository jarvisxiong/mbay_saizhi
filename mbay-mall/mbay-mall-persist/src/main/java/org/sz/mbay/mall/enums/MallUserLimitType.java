package org.sz.mbay.mall.enums;

/**
 * 兑换限制类型
 * 
 * @author frank.zong
 * 
 */
public enum MallUserLimitType {
	
	PERMANENT("永久"), EVERYDAY("每天");
	
	private String value;
	
	private MallUserLimitType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
