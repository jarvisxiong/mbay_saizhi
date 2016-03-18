package org.sz.mbay.channel.bean;

import org.joda.time.DateTime;
import org.sz.mbay.channel.enums.DepositState;
/**
 * 短信购买订单表
 * @author meibei-hrain
 *
 */
public class SMSPurchaseOrder {
	
	private int id;
	/* 订单编号  */
	private String orderId;
	/*  用户数量 */
	private String userNumber;
	/*  短信价格 */
	private int smsAmount;
	/*  美贝价格 */
	private double mbayAmount;
	/*  创建时间 */
	private DateTime createTime;
	/*  短信状态(1.未付款   2.时间) */
	private DepositState state;
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public int getSmsAmount() {
		return smsAmount;
	}
	
	public void setSmsAmount(int smsAmount) {
		this.smsAmount = smsAmount;
	}
	
	public double getMbayAmount() {
		return mbayAmount;
	}
	
	public void setMbayAmount(double mbayAmount) {
		this.mbayAmount = mbayAmount;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public DepositState getState() {
		return state;
	}
	
	public void setState(DepositState state) {
		this.state = state;
	}
}
