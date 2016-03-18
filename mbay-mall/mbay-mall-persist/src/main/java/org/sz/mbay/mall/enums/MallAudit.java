package org.sz.mbay.mall.enums;

/**
 * 兑换项审核(主要给后台人员查看)
 * 
 * @author frank.zong
 * 
 */
public enum MallAudit {
	
	NON(""), ONSHELF("上架审核中"), OFFSHELF("下架审核中"), CHANGE("变更审核中"), DELETE("删除审核中"), COMPLETE("审核通过");
	
	private String value;
	
	private MallAudit(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
