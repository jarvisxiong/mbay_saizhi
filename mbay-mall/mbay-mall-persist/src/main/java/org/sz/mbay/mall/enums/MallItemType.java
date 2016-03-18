package org.sz.mbay.mall.enums;

/**
 * 商品类型
 * 
 * @author frank.zong
 * 
 */
public enum MallItemType {
	
	ENTITY("实物"), COUPON("优惠券");
	
	private String value;
	
	private MallItemType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
