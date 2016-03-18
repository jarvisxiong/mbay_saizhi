package org.sz.mbay.trafficred.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.multipart.MultipartFile;
import org.sz.mbay.base.annotation.valid.Image;
import org.sz.mbay.base.annotation.valid.Url;
import org.sz.mbay.base.json.LongSerialize;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.trafficred.valid.group.CampaignCreate;
import org.sz.mbay.trafficred.valid.group.CampaignUpdate;

/**
 * MB送人分享信息配置
 * 
 * @author jerry
 */
public class TrafficRedMbayGiftConfig extends BaseEntityModel {
	
	private static final long serialVersionUID = -5706388173985524164L;
	
	// 对应的活动编号
	@JsonSerialize(using = LongSerialize.class)
	private Long campaignId;
	
	// 分享图片
	private String shareImg;
	
	// 分享图片 - 文件，非数据库字段
	@Image(groups = { CampaignCreate.class, CampaignUpdate.class })
	@NotNull(groups = { CampaignCreate.class })
	private MultipartFile giftShareImgFile;
	
	// 分享内容
	@Size(max = 255, groups = { CampaignCreate.class, CampaignUpdate.class })
	@NotNull(groups = { CampaignCreate.class, CampaignUpdate.class })
	private String shareContent;
	
	// 分享标题
	@Size(max = 255, groups = { CampaignCreate.class, CampaignUpdate.class })
	@NotNull(groups = { CampaignCreate.class, CampaignUpdate.class })
	private String shareTitle;
	
	// 参与链接
	@Size(max = 255, groups = { CampaignCreate.class, CampaignUpdate.class })
	@Url(groups = { CampaignCreate.class, CampaignUpdate.class })
	@NotNull(groups = { CampaignCreate.class, CampaignUpdate.class })
	private String participationLink;
	
	// 背景图
	private String bgImg;
	
	// 背景图 - 文件，非数据库字段
	@Image(groups = { CampaignCreate.class, CampaignUpdate.class })
	private MultipartFile giftBgImgFile;
	
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
	
	public String getShareContent() {
		return shareContent;
	}
	
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	
	public String getShareTitle() {
		return shareTitle;
	}
	
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	
	public String getParticipationLink() {
		return getFormatUrl(participationLink);
	}
	
	public void setParticipationLink(String participationLink) {
		this.participationLink = participationLink;
	}
	
	public String getBgImg() {
		return bgImg;
	}
	
	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}
	
	public MultipartFile getGiftShareImgFile() {
		return giftShareImgFile;
	}
	
	public void setGiftShareImgFile(MultipartFile giftShareImgFile) {
		this.giftShareImgFile = giftShareImgFile;
	}
	
	public MultipartFile getGiftBgImgFile() {
		return giftBgImgFile;
	}
	
	public void setGiftBgImgFile(MultipartFile giftBgImgFile) {
		this.giftBgImgFile = giftBgImgFile;
	}
	
	private String getFormatUrl(String url) {
		if (!StringUtils.isEmpty(url)) {
			if (!url.startsWith("http://") && !url.startsWith("https://")) {
				return "http://" + url;
			}
		}
		return url;
	}
}
