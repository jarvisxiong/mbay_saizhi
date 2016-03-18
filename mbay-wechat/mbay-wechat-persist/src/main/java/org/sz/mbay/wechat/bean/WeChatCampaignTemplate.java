package org.sz.mbay.wechat.bean;

import java.io.Serializable;

import org.sz.mbay.base.enums.EnableState;

/**
 * @Description: 微信伴侣模板
 * @author frank.zong
 * @date 2014-12-24 下午12:16:19
 * 
 */
@SuppressWarnings("serial")
public class WeChatCampaignTemplate implements Serializable {
	
	/**
	 * id
	 */
	private int id;
	/**
	 * 对应的活动编号
	 */
	private String eventnumber;
	/**
	 * 活动连接
	 */
	private String eventurl;
	/**
	 * 状态，启用，禁用 对应于Enable类
	 */
	private EnableState status;
	/**
	 * 分享图片
	 */
	private String shareImage;
	/**
	 * 模板背景图片
	 */
	private String backphoto;
	/**
	 * 模板button图片
	 */
	private String buttonphoto;
	
	/**
	 * 领取成功
	 */
	private String successPhoto;
	/**
	 * 分享次数
	 */
	private int shareTimes;
	/**
	 * 分享内容
	 */
	private String content;
	/**
	 * 分享标题
	 */
	private String shareTitle;
	/**
	 * 分享链接
	 */
	private String shareLink;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEventnumber() {
		return eventnumber;
	}
	
	public void setEventnumber(String eventnumber) {
		this.eventnumber = eventnumber;
	}
	
	public String getEventurl() {
		return eventurl;
	}
	
	public void setEventurl(String eventurl) {
		this.eventurl = eventurl;
	}
	
	public EnableState getStatus() {
		return status;
	}
	
	public void setStatus(EnableState status) {
		this.status = status;
	}

	public int getShareTimes() {
		return shareTimes;
	}
	
	public void setShareTimes(int shareTimes) {
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
		return shareLink;
	}
	
	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	public String getShareImage() {
		return shareImage;
	}

	public void setShareImage(String shareImage) {
		this.shareImage = shareImage;
	}
	
	public String getBackphoto() {
		return backphoto;
	}

	public void setBackphoto(String backphoto) {
		this.backphoto = backphoto;
	}

	public String getButtonphoto() {
		return buttonphoto;
	}
	
	public void setButtonphoto(String buttonphoto) {
		this.buttonphoto = buttonphoto;
	}
	
	public String getSuccessPhoto() {
		return successPhoto;
	}
	
	public void setSuccessPhoto(String successPhoto) {
		this.successPhoto = successPhoto;
	}
	
}