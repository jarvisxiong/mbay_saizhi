package org.sz.mbay.mall.enums;

/**
 * 状态
 * 
 * @author frank.zong
 * 
 */
public enum MallStatus {
	
	NON("失效"), OFF("下架"), ON("上架");
	
	private String value;
	
	private MallStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
