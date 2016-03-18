package org.sz.mbay.trafficred.bean;

import org.joda.time.DateTime;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.trafficred.enums.MbayGiftState;

/**
 * MB送人
 * 
 * @author jerry
 */
public class TrafficRedMbayGift extends BaseEntityModel {
	
	private static final long serialVersionUID = -7440512868450373909L;
	
	// 创建时间
	private DateTime createTime;
	
	// 送人状态
	private MbayGiftState mbayGiftState;
	
	// 拥有者
	private String sender;
	
	// 领取者
	private String reciever;
	
	// 关联送人扣款交易号
	private String serialNumber;
	
	// 送人金额
	private Double amount;
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public MbayGiftState getMbayGiftState() {
		return mbayGiftState;
	}
	
	public void setMbayGiftState(MbayGiftState mbayGiftState) {
		this.mbayGiftState = mbayGiftState;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getReciever() {
		return reciever;
	}
	
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
