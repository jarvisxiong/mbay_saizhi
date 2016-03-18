package org.sz.mbay.promotion.bean;

import java.io.Serializable;

/**
 * 分享信息
 * 
 * @author frank.zong
 * 		
 */
public class PromotionCampaignShare implements Serializable {
	
	private static final long serialVersionUID = -6079758427340242484L;
	
	// id
	private int id;
	
	// 活动编号
	private String campaignNumber;
	
	// 分享链接
	private String shareLink;
	
	// 分享标题
	private String shareTitle;
	
	// 分享内容
	private String content;
	
	// 分享有效次数
	private Integer shareTimes;
	
	// 分享图片
	private String shareImage;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCampaignNumber() {
		return campaignNumber;
	}
	
	public void setCampaignNumber(String campaignNumber) {
		this.campaignNumber = campaignNumber;
	}
	
	public String getShareLink() {
		return shareLink;
	}
	
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}
	
	public String getShareTitle() {
		return shareTitle;
	}
	
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getShareTimes() {
		return shareTimes;
	}
	
	public void setShareTimes(Integer shareTimes) {
		this.shareTimes = shareTimes;
	}
	
	public String getShareImage() {
		return shareImage;
	}
	
	public void setShareImage(String shareImage) {
		this.shareImage = shareImage;
	}
	
}
