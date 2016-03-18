package org.sz.mbay.trafficred.bean;

import org.joda.time.DateTime;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.trafficred.enums.PackageState;
import org.sz.mbay.trafficred.enums.RedeemType;

/**
 * 号码兑换记录
 * 
 * @author jerry
 */
public class TrafficRedExchangeRecord extends BaseEntityModel {
	
	private static final long serialVersionUID = 7911631740009732856L;
	
	// 活动编号
	private String campaignNumber;
	
	// 手机号码
	private String mobile;
	
	// 流量包id
	private Integer packageId;
	
	// 兑换时间
	private DateTime time;
	
	// 兑换类别
	private RedeemType redeemType;
	
	// 兑换内容
	private String content;
	
	// 红包状态
	private PackageState packageState;
	
	// 摇一摇实际操作的MB大小
	private Double size;
	
	public String getCampaignNumber() {
		return campaignNumber;
	}
	
	public void setCampaignNumber(String campaignNumber) {
		this.campaignNumber = campaignNumber;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public DateTime getTime() {
		return time;
	}
	
	public void setTime(DateTime time) {
		this.time = time;
	}
	
	public RedeemType getRedeemType() {
		return redeemType;
	}
	
	public void setRedeemType(RedeemType redeemType) {
		this.redeemType = redeemType;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public PackageState getPackageState() {
		return packageState;
	}
	
	public void setPackageState(PackageState packageState) {
		this.packageState = packageState;
	}
	
	public Integer getPackageId() {
		return packageId;
	}
	
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	
	public Double getSize() {
		return size;
	}
	
	public void setSize(Double size) {
		this.size = size;
	}
}
