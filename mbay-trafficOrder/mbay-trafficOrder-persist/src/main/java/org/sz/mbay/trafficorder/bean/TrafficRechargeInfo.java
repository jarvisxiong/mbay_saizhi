package org.sz.mbay.trafficorder.bean;

import org.sz.mbay.trafficorder.enums.TrafficOrderType;

public class TrafficRechargeInfo {
	
	///private TrafficRechargeInfo(){}
	
	/**流量充值手机号*/
	private String mobile;
	/**充值流量包*/
	private int trafficPackageNumber;
	/**关联号，用于查询您的充值单*/
	private String relationNumber;
	/**用户编号*/
	private String userNumber;
	/**充值类型*/
	private TrafficOrderType rechargeType;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getTrafficPackageNumber() {
		return trafficPackageNumber;
	}
	public void setTrafficPackageNumber(int trafficPackageNumber) {
		this.trafficPackageNumber = trafficPackageNumber;
	}
	public String getRelationNumber() {
		return relationNumber;
	}
	public void setRelationNumber(String relationNumber) {
		this.relationNumber = relationNumber;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public TrafficOrderType getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(TrafficOrderType rechargeType) {
		this.rechargeType = rechargeType;
	}
	
	
	
	
	

}
 
