package org.sz.mbay.mall.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.mall.enums.MallPictureType;

/**
 * 商品图片
 * 
 * @author frank.zong
 */
public class MallPicture implements Serializable {
	
	private static final long serialVersionUID = 6410494273805738196L;
	
	/** id **/
	private int id;
	
	/** 兑换项编号 **/
	private String itemNumber;
	
	/** 图片类型 **/
	private MallPictureType type;
	
	/** 图片地址 **/
	private String picture;
	
	/** 创建时间 **/
	private DateTime createTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getItemNumber() {
		return itemNumber;
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	public MallPictureType getType() {
		return type;
	}
	
	public void setType(MallPictureType type) {
		this.type = type;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
}
