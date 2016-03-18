package org.sz.mbay.trafficred.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.sz.mbay.base.json.LongSerialize;
import org.sz.mbay.base.model.BaseEntityModel;

/**
 * 流量红包流量包产品
 * 
 * @author Fenlon
 * 		
 */
public class TrafficRedCampaignPackage extends BaseEntityModel {
	
	private static final long serialVersionUID = 2130132389331487174L;
	
	@JsonSerialize(using = LongSerialize.class)
	private Long campaignId;
	
	private TrafficRedPackage trafficPackage;
	
	private Integer ratio;
	
	public Long getCampaignId() {
		return campaignId;
	}
	
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
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
