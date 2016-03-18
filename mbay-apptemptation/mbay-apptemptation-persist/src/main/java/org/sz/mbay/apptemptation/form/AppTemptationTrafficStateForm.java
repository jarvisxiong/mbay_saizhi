//package org.sz.mbay.apptemptation.form;
//
//import org.apache.commons.lang.StringUtils;
//import org.joda.time.DateTime;
//import org.sz.mbay.base.common.utils.MbayDateFormat;
//import org.sz.mbay.trafficrecharge.enums.OperatorRechargeStatus;
//
///**
// * app诱惑充值记录查询表单
// * 
// * @author jerry
// */
//public class AppTemptationTrafficStateForm {
//	
//	private String campaignNumber;
//	
//	private DateTime startTime;
//	
//	private DateTime endTime;
//	
//	private OperatorRechargeStatus oprState;
//	
//	public String getCampaignNumber() {
//		return campaignNumber;
//	}
//	
//	public void setCampaignNumber(String campaignNumber) {
//		this.campaignNumber = campaignNumber;
//	}
//	
//	public DateTime getStartTime() {
//		return startTime;
//	}
//	
//	public void setStartTime(String startTime) {
//		if (!StringUtils.isEmpty(startTime)) {
//			startTime += " 00:00:00";
//			this.startTime = MbayDateFormat.stringToTime(startTime);
//		} else {
//			this.startTime = null;
//		}
//	}
//	
//	public DateTime getEndTime() {
//		return endTime;
//	}
//	
//	public void setEndTime(String endTime) {
//		if (!StringUtils.isEmpty(endTime)) {
//			endTime += " 23:59:59";
//			this.endTime = MbayDateFormat.stringToTime(endTime);
//		} else {
//			this.endTime = null;
//		}
//	}
//	
//	public OperatorRechargeStatus getOprState() {
//		return oprState;
//	}
//	
//	public void setOprState(OperatorRechargeStatus oprState) {
//		this.oprState = oprState;
//	}
//}
