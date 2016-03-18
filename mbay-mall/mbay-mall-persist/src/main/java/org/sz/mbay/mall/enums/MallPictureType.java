package org.sz.mbay.mall.enums;

/**
 * 图片类型
 * 
 * @author frank.zong
 * 
 */
public enum MallPictureType {
	
	DETAIL("详情图"), THUMBNAIL("缩略图"), ICON("图标"), BANNER("首页Banner");
	
	private String value;
	
	private MallPictureType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
