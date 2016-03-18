package org.sz.mbay.wallet.bean;

import org.sz.mbay.base.model.BaseEntityModel;

/**
 * @Description: 微信分享实体bean
 * @author frank.zong
 * @date 2015-4-21 上午9:09:09
 * 
 */
public class WeChatShare extends BaseEntityModel {
	
	private static final long serialVersionUID = -3306461451752264999L;
	
	/** 分享标题 **/
	private String title;
	
	/** 分享链接 **/
	private String link;
	
	/** 分享内容 **/
	private String content;
	
	/** 分享图片 **/
	private String image;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
}
