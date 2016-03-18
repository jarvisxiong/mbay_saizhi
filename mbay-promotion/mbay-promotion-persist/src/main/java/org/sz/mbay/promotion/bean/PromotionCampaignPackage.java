package org.sz.mbay.promotion.bean;

import java.io.Serializable;

import org.sz.mbay.trafficred.bean.TrafficRedPackage;

/**
 * 流量产品
 * 
 * @author frank.zong
 * 		
 */
public class PromotionCampaignPackage implements Serializable {
	
	private static final long serialVersionUID = 3582544871940460182L;
	
	// id
	private int id;
	
	// 活动编号
	private String campaignNumber;
	
	// 流量产品
	private TrafficRedPackage trafficPackage;
	
	// 概率
	private Integer ratio;
	
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
	
	public TrafficRedPackage getTrafficPackage() {
		return trafficPackage;
	}
	
	public void setTrafficPackage(TrafficRedPackage trafficPackage) {
		this.trafficPackage = trafficPackage;
	}
	
	public Integer getRatio() {
		return ratio;
	}
	
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	
}
