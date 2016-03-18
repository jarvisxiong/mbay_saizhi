package org.sz.mbay.channel.useraccount.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ONECITY 用户账户
 * 
 */
public class UserAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -652934749257255586L;

	/* 用户ID */
	private String userNumber;

	/* 账户编号 */
	private String accountnumber;

	/** 账户总余额 */
	private BigDecimal amount;
	/** 锁定余额* */
	private BigDecimal lockedamount;

	/**信用额度*** */
	private double creditLimit;

	/** 状态0：生效，冻结，注销 */
	private int status;

	/* 兑换密码 */
	private String redeempwd;
	

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getLockedamount() {
		return lockedamount;
	}

	public void setLockedamount(BigDecimal lockedamount) {
		this.lockedamount = lockedamount;
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRedeempwd() {
		return redeempwd;
	}

	public void setRedeempwd(String redeempwd) {
		this.redeempwd = redeempwd;
	}
	
	

}
