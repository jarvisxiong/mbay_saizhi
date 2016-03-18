package org.sz.mbay.trafficorder.qo;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;

public class TrafficOrderQO {
	
	private DateTime startTime;
	
	private DateTime endTime;
	
	private TrafficOrderType orderType;
	
	private String relateNumber;
	
	private String userNumber;
	
	private String orderNumber;
	
	private String mobile;
	
	private OperatorRechargeStatus ors;
	
	private TrafficOrderStatus status;
	
	public DateTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		if (!StringUtils.isEmpty(startTime)) {
			this.startTime = MbayDateFormat.stringToTime(startTime
					+ " 00:00:00");
		}
	}
	
	public DateTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		if (!StringUtils.isEmpty(endTime)) {
			this.endTime = MbayDateFormat.stringToTime(endTime + " 23:59:59");
		}
	}
	
	public TrafficOrderType getOrderType() {
		return orderType;
	}
	
	public void setOrderType(TrafficOrderType orderType) {
		this.orderType = orderType;
	}
	
	public String getRelateNumber() {
		return relateNumber;
	}
	
	public void setRelateNumber(String relateNumber) {
		this.relateNumber = relateNumber;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public OperatorRechargeStatus getOrs() {
		return ors;
	}
	
	public void setOrs(OperatorRechargeStatus ors) {
		this.ors = ors;
	}
	
	public TrafficOrderStatus getStatus() {
		return status;
	}
	
	public void setStatus(TrafficOrderStatus status) {
		this.status = status;
	}
	
}
