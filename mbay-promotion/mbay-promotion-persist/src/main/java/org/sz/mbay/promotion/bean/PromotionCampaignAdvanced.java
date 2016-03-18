package org.sz.mbay.promotion.bean;

import java.io.Serializable;

import org.sz.mbay.base.enums.EnableState;

/**
* @Description: 促销神器开发者模式
* @author frank.zong
* @date 2015-7-9 下午12:16:19 
*
 */
@SuppressWarnings("serial")
public class PromotionCampaignAdvanced implements Serializable {
	/**
	 * id
	 */
	private int id;
	/**
	 * 对应的活动编号
	 */
	private String campaignNumber;
	/**
	 * 状态，启用，禁用 对应于Enable类
	 */
	private EnableState status;	
	/**
	 * token
	 */
	private String token;
	
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
	public EnableState getStatus() {
		return status;
	}
	public void setStatus(EnableState status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}