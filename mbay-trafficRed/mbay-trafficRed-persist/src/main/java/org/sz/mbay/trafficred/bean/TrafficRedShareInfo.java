package org.sz.mbay.trafficred.bean;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;
import org.sz.mbay.base.annotation.valid.Image;
import org.sz.mbay.base.annotation.valid.Url;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.base.json.LongSerialize;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.trafficred.valid.group.CampaignCreate;
import org.sz.mbay.trafficred.valid.group.CampaignUpdate;

/**
 * 分享信息
 * 
 * @author jerry
 */
public class TrafficRedShareInfo extends BaseEntityModel {
	
	private static final long serialVersionUID = -4939665341246926657L;
	
	// 对应的活动编号
	@JsonSerialize(using = LongSerialize.class)
	private Long campaignId;
	
	// 分享图片
	private String shareImg;
	
	// 分享图片 - 文件，非数据库字段
	@Image(groups = { CampaignCreate.class, CampaignUpdate.class })
	private MultipartFile shareImgFile;
	
	// 分享次数
	@DecimalMin(value = "0",
			groups = { CampaignCreate.class, CampaignUpdate.class })
	@DecimalMax(value = "999",
			groups = { CampaignCreate.class, CampaignUpdate.class })
	private Integer shareTimes;
	
	// 分享内容
	@Size(max = 255, groups = { CampaignCreate.class, CampaignUpdate.class })
	private String content;
	
	// 分享标题
	@Size(max = 255, groups = { CampaignCreate.class, CampaignUpdate.class })
	private String shareTitle;
	
	// 分享链接
	@Size(max = 255, groups = { CampaignCreate.class, CampaignUpdate.class })
	@Url(groups = { CampaignCreate.class, CampaignUpdate.class })
	private String shareLink;
	
	// 禁用/可用
	@NotNull(groups = { CampaignCreate.class, CampaignUpdate.class })
	private EnableState enableState;
	
	public EnableState getEnableState() {
		return enableState;
	}
	
	public void setEnableState(EnableState enableState) {
		this.enableState = enableState;
	}
	
	public Long getCampaignId() {
		return campaignId;
	}
	
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}
	
	public String getShareImg() {
		return shareImg;
	}
	
	public void setShareImg(String shareImg) {
		this.shareImg = shareImg;
	}
	
	public Integer getShareTimes() {
		return shareTimes;
	}
	
	public void setShareTimes(Integer shareTimes) {
		this.shareTimes = shareTimes;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getShareTitle() {
		return shareTitle;
	}
	
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	
	public String getShareLink() {
		return formatUrl(shareLink);
	}
	
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}
	
	public MultipartFile getShareImgFile() {
		return shareImgFile;
	}
	
	public void setShareImgFile(MultipartFile shareImgFile) {
		this.shareImgFile = shareImgFile;
	}
	
	private String formatUrl(String url) {
		if (!StringUtils.isEmpty(url)) {
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				return "http://" + url;
			}
		}
		return url;
	}
	
}
