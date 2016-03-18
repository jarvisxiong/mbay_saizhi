package org.sz.mbay.trafficred.bean;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.sz.mbay.base.json.LongSerialize;
import org.sz.mbay.base.model.BaseEntityModel;

/**
 * 美贝流量产品实体
 * 
 * @author Fenlon
 * 
 */
public class TrafficRedCampaignMbay extends BaseEntityModel {
	
	private static final long serialVersionUID = -7245038647297836189L;
	
	@JsonSerialize(using = LongSerialize.class)
	private Long campaignId;
	
	private TrafficRedMbay mbay;
	
	private Integer ratio;
	
	public Long getCampaignId() {
		return campaignId;
	}
	
	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
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
