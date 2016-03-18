package org.sz.mbay.trafficred.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.sz.mbay.base.json.LongSerialize;

public class TrafficRedMbayDTO implements Serializable {
	
	private static final long serialVersionUID = -1087298244374584415L;
	
	// TrafficRedMbay id
	@JsonSerialize(using = LongSerialize.class)
	private Long mbayId;
	
	// 美贝
	private Integer mbay;
	
	// 权重
	private Integer ratio;
	
	public Integer getMbay() {
		return mbay;
	}
	
	public void setMbay(Integer mbay) {
		this.mbay = mbay;
	}
	
	public Integer getRatio() {
		return ratio;
	}
	
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	
	public Long getMbayId() {
		return mbayId;
	}
	
	public void setMbayId(Long mbayId) {
		this.mbayId = mbayId;
	}
	
}
