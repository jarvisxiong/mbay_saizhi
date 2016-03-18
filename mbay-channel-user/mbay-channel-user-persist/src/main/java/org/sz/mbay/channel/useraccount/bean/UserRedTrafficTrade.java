package org.sz.mbay.channel.useraccount.bean;

import java.io.Serializable;

import org.sz.mbay.channel.user.enums.PaymentType;

/** 
* @Description: 用户账户交易关联
* @author han.han 
* @date 2015-4-7 下午5:56:13 
*  
*/
public class UserRedTrafficTrade implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7452728926874396191L;

	/**
	 * 用户编号
	 */
	private String userNumber;
	
	/**
	 * 交易记录
	 */
	private RedTrafficTradeRecord tradeRecord;
	
	/**
	 * 收入支出类型
	 */
	private PaymentType paymentType;
	
	/**
	 * 当前交易后账户余额
	 */
	private double balance;

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public RedTrafficTradeRecord getTradeRecord() {
		return tradeRecord;
	}

	public void setTradeRecord(RedTrafficTradeRecord tradeRecord) {
		this.tradeRecord = tradeRecord;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	

	

}
