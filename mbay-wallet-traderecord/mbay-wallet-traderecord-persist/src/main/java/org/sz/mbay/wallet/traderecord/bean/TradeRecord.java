//package org.sz.mbay.wallet.traderecord.bean;
//
//import java.math.BigDecimal;
//
//import org.joda.time.DateTime;
//import org.sz.mbay.base.model.BaseEntityModel;
//import org.sz.mbay.wallet.traderecord.enums.AccountType;
//import org.sz.mbay.wallet.traderecord.enums.PaymentType;
//import org.sz.mbay.wallet.traderecord.enums.TradeType;
//
///**
// * 交易记录
// * 
// * @author jerry
// */
//public class TradeRecord extends BaseEntityModel {
//	
//	private static final long serialVersionUID = 1178892924930678839L;
//	
//	// 流水号
//	private String serialNumber;
//	
//	// 交易影响的账户类型
//	private AccountType accountType;
//	
//	// 交易类型
//	private TradeType tradeType;
//	
//	// 交易描述
//	private String description;
//	
//	// 交易发起方
//	private String masterTelephone;
//	
//	// 交易接受方
//	private String reciprocalTelephone;
//	
//	// 支付类型
//	private PaymentType paymentType;
//	
//	// 交易金额（美贝）
//	private BigDecimal amount;
//	
//	// 相关单号
//	private String relatedNumber;
//	
//	// 交易创建时间
//	private DateTime createTime;
//	
//	public String getSerialNumber() {
//		return serialNumber;
//	}
//	
//	public void setSerialNumber(String serialNumber) {
//		this.serialNumber = serialNumber;
//	}
//	
//	public AccountType getAccountType() {
//		return accountType;
//	}
//	
//	public void setAccountType(AccountType accountType) {
//		this.accountType = accountType;
//	}
//	
//	public TradeType getTradeType() {
//		return tradeType;
//	}
//	
//	public void setTradeType(TradeType tradeType) {
//		this.tradeType = tradeType;
//	}
//	
//	public String getDescription() {
//		return description;
//	}
//	
//	public void setDescription(String description) {
//		this.description = description;
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
//	public BigDecimal getAmount() {
//		return amount;
//	}
//	
//	public void setAmount(BigDecimal amount) {
//		this.amount = amount;
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
//	public String getReciprocalTelephone() {
//		return reciprocalTelephone;
//	}
//	
//	public void setReciprocalTelephone(String reciprocalTelephone) {
//		this.reciprocalTelephone = reciprocalTelephone;
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
