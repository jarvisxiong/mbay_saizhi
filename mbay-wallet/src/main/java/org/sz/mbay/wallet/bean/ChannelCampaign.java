package org.sz.mbay.wallet.bean;

import org.sz.mbay.base.model.BaseEntityModel;

/**
 * @Description: 商户活动实体bean
 * @author frank.zong
 * @date 2015-4-21 下午15:09:09
 * 
 */
public class ChannelCampaign extends BaseEntityModel {
	
	private static final long serialVersionUID = 4650380394596062829L;
	
	/** 名称 **/
	private String name;
	
	/** 链接 **/
	private String link;
	
	/** 图片 **/
	private String image;
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
