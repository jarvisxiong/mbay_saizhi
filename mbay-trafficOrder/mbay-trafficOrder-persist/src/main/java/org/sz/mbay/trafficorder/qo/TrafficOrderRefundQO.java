package org.sz.mbay.trafficorder.qo;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.sz.mbay.common.util.MbayDateFormat;

public class TrafficOrderRefundQO {
	
	private DateTime startTime;
	
	private DateTime endTime;
	
	private String relateNumber;
	
	private String userNumber;
	
	private String orderNumber;
	
	private String mobile;
	
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
}
