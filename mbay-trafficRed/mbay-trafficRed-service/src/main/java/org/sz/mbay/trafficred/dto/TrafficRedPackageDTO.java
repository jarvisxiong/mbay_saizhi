package org.sz.mbay.trafficred.dto;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.sz.mbay.base.json.LongSerialize;
import org.sz.mbay.operator.enums.OperatorType;

public class TrafficRedPackageDTO implements Serializable {
	
	private static final long serialVersionUID = 5108909532945244332L;
	
	// TrafficRedCampaignPackage id
	@JsonSerialize(using = LongSerialize.class)
	private Long campaignBeanId;
	
	// 流量包id
	private Integer packageId;
	
	// 流量大小
	private Integer traffic;
	
	// 权重
	private Integer ratio;
	
	// 运营商
	private OperatorType operatorType;
	
	public Long getCampaignBeanId() {
		return campaignBeanId;
	}
	
	public void setCampaignBeanId(Long campaignBeanId) {
		this.campaignBeanId = campaignBeanId;
	}
	
	public Integer getPackageId() {
		return packageId;
	}
	
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	
	public Integer getTraffic() {
		return traffic;
	}
	
	public void setTraffic(Integer traffic) {
		this.traffic = traffic;
	}
	
	public Integer getRatio() {
		return ratio;
	}
	
	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	
	public OperatorType getOperatorType() {
		return operatorType;
	}
	
	public void setOperatorType(OperatorType operatorType) {
		this.operatorType = operatorType;
	}
}
