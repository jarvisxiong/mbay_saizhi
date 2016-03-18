package org.sz.mbay.channel.enums;

/**
 * @Description: 汇款记录状态
 * @author frank.zong
 * @date 2014-10-21 下午13:27:55
 * 
 */
public enum RemittanceStatus {
	
	NON(""), UNPROCESSED("未处理"), PROCESSED("已处理"), CANCLED("已取消");
	
	public String value;
	
	private RemittanceStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
