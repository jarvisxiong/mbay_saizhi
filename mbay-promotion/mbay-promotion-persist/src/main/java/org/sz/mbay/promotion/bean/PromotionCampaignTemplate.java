package org.sz.mbay.promotion.bean;

import java.io.Serializable;

/**
 * @Description: 促销神器模板
 * @author frank.zong
 * @date 2015-1-8 下午12:16:19
 * 		
 */
@SuppressWarnings("serial")
public class PromotionCampaignTemplate implements Serializable {
	
	/**
	 * id
	 */
	private int id;
	/**
	 * 对应的活动编号
	 */
	private String eventnumber;
	/**
	 * 领取链接
	 */
	private String eventurl;
	/**
	 * 兑换连接
	 */
	private String redeemurl;
	/**
	 * 领取页面背景图片
	 */
	private String backphoto;
	/**
	 * 兑换页面背景图片
	 */
	private String redeemBackphoto;
	/**
	 * 兑换码领取说明
	 */
	private String introduction;
	/**
	 * 领取页面按钮文字
	 */
	private String gotText;
	/**
	 * 兑换页面按钮1文字
	 */
	private String redeemText;
	/**
	 * 兑换页面按钮2文字
	 */
	private String introductionText;
	
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
	
	public String getRedeemurl() {
		return redeemurl;
	}
	
	public void setRedeemurl(String redeemurl) {
		this.redeemurl = redeemurl;
	}
	
	public String getBackphoto() {
		return backphoto;
	}
	
	public void setBackphoto(String backphoto) {
		this.backphoto = backphoto;
	}
	
	public String getRedeemBackphoto() {
		return redeemBackphoto;
	}
	
	public void setRedeemBackphoto(String redeemBackphoto) {
		this.redeemBackphoto = redeemBackphoto;
	}
	
	public String getIntroduction() {
		return introduction;
	}
	
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	
	public String getGotText() {
		return gotText;
	}
	
	public void setGotText(String gotText) {
		this.gotText = gotText;
	}
	
	public String getRedeemText() {
		return redeemText;
	}
	
	public void setRedeemText(String redeemText) {
		this.redeemText = redeemText;
	}
	
	public String getIntroductionText() {
		return introductionText;
	}
	
	public void setIntroductionText(String introductionText) {
		this.introductionText = introductionText;
	}
}
