package org.sz.mbay.trafficred.bean;

import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;
import org.sz.mbay.base.annotation.valid.Image;
import org.sz.mbay.base.annotation.valid.Url;
import org.sz.mbay.base.json.LongSerialize;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.trafficred.valid.group.CampaignCreate;
import org.sz.mbay.trafficred.valid.group.TemplateUpdate;

/**
 * 流量红包活动模板
 * 
 * @author Fenlon
 * 		
 */
public class TrafficRedTemplate extends BaseEntityModel {
	
	private static final long serialVersionUID = 5689897561222662513L;
	
	// 对应的活动编号
	@JsonSerialize(using = LongSerialize.class)
	private Long campaignId;
	
	// 广告背景1
	private String adImg1;
	
	// 广告背景1 - 文件，非数据库字段
	@Image(groups = { CampaignCreate.class, TemplateUpdate.class })
	private MultipartFile adImg1File;
	
	// 广告背景2
	private String adImg2;
	
	// 广告背景2 - 文件，非数据库字段
	@Image(groups = { CampaignCreate.class, TemplateUpdate.class })
	private MultipartFile adImg2File;
	
	// 广告链接1
	@Size(max = 255, groups = { CampaignCreate.class, TemplateUpdate.class })
	@Url(groups = { CampaignCreate.class, TemplateUpdate.class })
	private String adLink1;
	
	// 广告链接2
	@Size(max = 255, groups = { CampaignCreate.class, TemplateUpdate.class })
	@Url(groups = { CampaignCreate.class, TemplateUpdate.class })
	private String adLink2;
	
	// 摇一摇结果链接
	@Size(max = 255, groups = { CampaignCreate.class, TemplateUpdate.class })
	@Url(groups = { CampaignCreate.class, TemplateUpdate.class })
	private String sharkResultLink;
	
	// logo圆标链接
	@Size(max = 255, groups = { CampaignCreate.class, TemplateUpdate.class })
	@Url(groups = { CampaignCreate.class, TemplateUpdate.class })
	private String logoCycleLink;
	
	// 摇一摇首页图
	private String shakeIndexImg;
	
	// 摇一摇首页图 - 文件，非数据库字段
	@Image(groups = { CampaignCreate.class, TemplateUpdate.class })
	private MultipartFile shakeIndexImgFile;
	
	// 摇一摇抽奖图
	private String shakeUIImg;
	
	// 摇一摇抽奖图 - 文件，非数据库字段
	@Image(groups = { CampaignCreate.class, TemplateUpdate.class })
	private MultipartFile shakeUIImgFile;
	
	// 摇一摇图片增加时间
	private DateTime shakeImgsAddTime;
	
	// 关联活动
	private TrafficRedCampaign campaign;
	
	public DateTime getShakeImgsAddTime() {
		return shakeImgsAddTime;
	}
	
	public void setShakeImgsAddTime(DateTime shakeImgsAddTime) {
		this.shakeImgsAddTime = shakeImgsAddTime;
	}
	
	public Long getCampaignId() {
		return campaignId;
	}
	
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}
	
	public String getAdImg1() {
		return adImg1;
	}
	
	public void setAdImg1(String adImg1) {
		this.adImg1 = adImg1;
	}
	
	public String getAdImg2() {
		return adImg2;
	}
	
	public void setAdImg2(String adImg2) {
		this.adImg2 = adImg2;
	}
	
	public String getAdLink1() {
		return getFormatUrl(adLink1);
	}
	
	public void setAdLink1(String adLink1) {
		this.adLink1 = adLink1;
	}
	
	public String getAdLink2() {
		return getFormatUrl(adLink2);
	}
	
	public void setAdLink2(String adLink2) {
		this.adLink2 = adLink2;
	}
	
	public String getSharkResultLink() {
		return getFormatUrl(sharkResultLink);
	}
	
	public void setSharkResultLink(String sharkResultLink) {
		this.sharkResultLink = sharkResultLink;
	}
	
	public String getLogoCycleLink() {
		return getFormatUrl(logoCycleLink);
	}
	
	public void setLogoCycleLink(String logoCycleLink) {
		this.logoCycleLink = logoCycleLink;
	}
	
	public MultipartFile getAdImg1File() {
		return adImg1File;
	}
	
	public void setAdImg1File(MultipartFile adImg1File) {
		this.adImg1File = adImg1File;
	}
	
	public MultipartFile getAdImg2File() {
		return adImg2File;
	}
	
	public void setAdImg2File(MultipartFile adImg2File) {
		this.adImg2File = adImg2File;
	}
	
	public String getShakeIndexImg() {
		return shakeIndexImg;
	}
	
	public void setShakeIndexImg(String shakeIndexImg) {
		this.shakeIndexImg = shakeIndexImg;
	}
	
	public MultipartFile getShakeIndexImgFile() {
		return shakeIndexImgFile;
	}
	
	public void setShakeIndexImgFile(MultipartFile shakeIndexImgFile) {
		this.shakeIndexImgFile = shakeIndexImgFile;
	}
	
	public String getShakeUIImg() {
		return shakeUIImg;
	}
	
	public void setShakeUIImg(String shakeUIImg) {
		this.shakeUIImg = shakeUIImg;
	}
	
	public MultipartFile getShakeUIImgFile() {
		return shakeUIImgFile;
	}
	
	public void setShakeUIImgFile(MultipartFile shakeUIImgFile) {
		this.shakeUIImgFile = shakeUIImgFile;
	}
	
	public TrafficRedCampaign getCampaign() {
		return campaign;
	}
	
	public void setCampaign(TrafficRedCampaign campaign) {
		this.campaign = campaign;
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
