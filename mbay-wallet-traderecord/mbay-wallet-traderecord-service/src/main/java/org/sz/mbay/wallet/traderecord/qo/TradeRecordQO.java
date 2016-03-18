//package org.sz.mbay.wallet.traderecord.qo;
//
//import org.joda.time.DateTime;
//import org.springframework.util.StringUtils;
//import org.sz.mbay.common.util.MbayDateFormat;
//import org.sz.mbay.wallet.traderecord.enums.PaymentType;
//import org.sz.mbay.wallet.traderecord.enums.TradeType;
//
///**
// * 交易明细查询对象
// * 
// * @author jerry
// */
//public class TradeRecordQO {
//	
//	// 开始时间
//	private DateTime startTime;
//	
//	// 结束时间
//	private DateTime endTime;
//	
//	// 主账户
//	private String masterTelephone;
//	
//	// 收入/支出
//	private PaymentType paymentType;
//	
//	// 交易类型
//	private TradeType tradeType;
//	
//	public TradeType getTradeType() {
//		return tradeType;
//	}
//	
//	public void setTradeType(TradeType tradeType) {
//		this.tradeType = tradeType;
//	}
//	
//	public DateTime getStartTime() {
//		return startTime;
//	}
//	
//	public void setStartTime(String startTime) {
//		if (!StringUtils.isEmpty(startTime)) {
//			this.startTime = MbayDateFormat
//					.stringToTime(startTime + " 00:00:00");
//		}
//	}
//	
//	public DateTime getEndTime() {
//		return endTime;
//	}
//	
//	public void setEndTime(String endTime) {
//		if (!StringUtils.isEmpty(startTime)) {
//			this.endTime = MbayDateFormat.stringToTime(endTime + " 23:59:59");
//		}
//	}
//	
//	public String getMasterTelephone() {
//		return masterTelephone;
//	}
//	
//	public void setMasterTelephone(String masterTelephone) {
//		this.masterTelephone = masterTelephone;
//	}
//	
//	public PaymentType getPaymentType() {
//		return paymentType;
//	}
//	
//	public void setPaymentType(PaymentType paymentType) {
//		this.paymentType = paymentType;
//	}
//}
