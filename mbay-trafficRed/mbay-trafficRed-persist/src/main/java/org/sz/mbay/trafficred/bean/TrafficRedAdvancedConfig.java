package org.sz.mbay.trafficred.bean;

import java.io.Serializable;

/**
 * @author han.han
 * 流量红包开发这模式配置信息
 *
 */
public class TrafficRedAdvancedConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1747133059531363333L;
	
	/**
	 * 活动ID
	 */
	private long campaignId;
	
	/**
	 * 密钥
	 */
	private String key;
	
	/**
	 * 启用状态
	 */
	private boolean enabled;

	
	public long getCampaignId() {
		return campaignId;
	}

	
	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}

	
	public String getKey() {
		return key;
	}

	
	public void setKey(String key) {
		this.key = key;
	}

	
	public boolean isEnabled() {
		return enabled;
	}

	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
