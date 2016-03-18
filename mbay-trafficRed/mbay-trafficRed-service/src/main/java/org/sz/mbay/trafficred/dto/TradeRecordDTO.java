package org.sz.mbay.trafficred.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.sz.mbay.channel.user.enums.PaymentType;


/**
 * 交易明细数据传输对象
 * 
 * @author jerry
 */
public class TradeRecordDTO {
	
	// 交易描述
	private String description;
	
	// 支付类型
	private PaymentType paymentType;
	
	// 交易金额（美贝）
	private BigDecimal amount;
	
	// 交易创建时间
	private Date createTime;
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
