package org.sz.mbay.promotion.bean;

import java.io.Serializable;
import org.sz.mbay.trafficred.bean.TrafficRedMbay;

/**
 * 美贝产品
 * 
 * @author frank.zong
 * 		
 */
public class PromotionCampaignMbay implements Serializable {
	
	private static final long serialVersionUID = -6079758427340242484L;
	
	// id
	private int id;
	
	// 活动编号
	private String campaignNumber;
	
	// 美贝产品
	private TrafficRedMbay mbay;
	
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
	
	public TrafficRedMbay getMbay() {
		return mbay;
	}
	
	public void setMbay(TrafficRedMbay mbay) {
		this.mbay = mbay;
	}
	
	public Integer getRatio() {
		return ratio;
	}
	
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	
}
