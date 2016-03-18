package org.sz.mbay.particularcase.pingan.trafficred.enums;

/**
 * 自身处理状态
 * 
 * @author frank.zong
 * 		
 */
public enum MbayStatus {
	NOT("未处理"), FINISH("已处理");
	
	private String value;
	
	private MbayStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
