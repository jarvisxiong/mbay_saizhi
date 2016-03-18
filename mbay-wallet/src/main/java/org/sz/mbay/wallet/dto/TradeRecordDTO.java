//package org.sz.mbay.wallet.dto;
//
//import java.math.BigDecimal;
//
//import org.joda.time.DateTime;
//import org.sz.mbay.wallet.traderecord.enums.PaymentType;
//
///**
// * 交易明细数据传输对象
// * 
// * @author jerry
// */
//public class TradeRecordDTO {
//	
//	// 交易描述
//	private String description;
//	
//	// 支付类型
//	private PaymentType paymentType;
//	
//	// 交易金额（美贝）
//	private BigDecimal amount;
//	
//	// 交易创建时间
//	private DateTime createTime;
//	
//	// 相关单号
//	private String relatedNumber;
//	
//	public String getDescription() {
//		return description;
//	}
//	
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	
//	public BigDecimal getAmount() {
//		return amount;
//	}
//	
//	public void setAmount(BigDecimal amount) {
//		this.amount = amount;
//	}
//	
//	public DateTime getCreateTime() {
//		return createTime;
//	}
//	
//	public void setCreateTime(DateTime createTime) {
//		this.createTime = createTime;
//	}
//	
//	public PaymentType getPaymentType() {
//		return paymentType;
//	}
//	
//	public void setPaymentType(PaymentType paymentType) {
//		this.paymentType = paymentType;
//	}
//	
//	public String getRelatedNumber() {
//		return relatedNumber;
//	}
//	
//	public void setRelatedNumber(String relatedNumber) {
//		this.relatedNumber = relatedNumber;
//	}
//}
