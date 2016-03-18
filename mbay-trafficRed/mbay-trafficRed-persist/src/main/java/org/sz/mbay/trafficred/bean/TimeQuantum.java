package org.sz.mbay.trafficred.bean;

import java.sql.Time;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.sz.mbay.base.json.LongSerialize;
import org.sz.mbay.base.model.BaseEntityModel;

/**
 * 表示时间段类
 * 
 * @author Fenlon
 */
public class TimeQuantum extends BaseEntityModel {
	
	private static final long serialVersionUID = 7473835741274393736L;
	
	/** 开始日期 **/
	private Time startTime;
	
	/** 结束日期 **/
	private Time endTime;
	
	/** 流量红包 **/
	@JsonSerialize(using = LongSerialize.class)
	private Long trafficRedCampaignId;
	
	public Time getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	public Long getTrafficRedCampaignId() {
		return trafficRedCampaignId;
	}
	
	public void setTrafficRedCampaignId(Long trafficRedCampaignId) {
		this.trafficRedCampaignId = trafficRedCampaignId;
	}
}
