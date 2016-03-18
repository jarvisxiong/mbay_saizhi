package org.sz.mbay.apptemptation.bean;

import java.io.Serializable;

/**
 * app诱惑绑定ip
 * 
 * @author jerry
 */
public class AppTemptationBindIp implements Serializable {
	
	private static final long serialVersionUID = 7343142436780648461L;
	
	// 主键
	private Long id;
	
	// 加密字符串型id
	private String idStr;
	
	// 活动编号
	private String campaignNumber;
	
	// ip
	private String ip;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getCampaignNumber() {
		return campaignNumber;
	}
	
	public void setCampaignNumber(String campaignNumber) {
		this.campaignNumber = campaignNumber;
	}
	
	public String getIdStr() {
		return idStr;
	}
	
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	
}
