package org.sz.mbay.wechat.bean;

import java.io.Serializable;

import org.sz.mbay.base.enums.EnableState;

/**
* @Description: 微信伴侣开发者模式
* @author frank.zong
* @date 2014-12-24 下午12:16:19 
*
 */
@SuppressWarnings("serial")
public class WeChatCampaignAdvanced implements Serializable {
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