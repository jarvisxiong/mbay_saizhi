package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.promotion.enums.RedeemCodeStu;

public class O2OCampaignRedeenDetail implements Serializable {
	
	private static final long serialVersionUID = -4025169580529343852L;
	/** 兑换码 **/
	private String redeemCode;
	/** 核销码 **/
	private String checkCode;
	/** 用户手机号 **/
	private String userCellPhone;
	/** 兑换状态 **/
	private RedeemCodeStu status;
	/** 发放门店编号 **/
	private String deliverStoreNumber;
	/** 发放时间 **/
	private DateTime deliverDate;
	/** 操作员手机号 可能为空 **/
	private String operatorCellPhone;
	/** 兑换门店编号 可能为空 **/
	private String RedeemStoreNumber;
	/** 兑换码日期 可能为空 **/
	private DateTime redeemDate;
	
	public String getRedeemCode() {
		return redeemCode;
	}
	
	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}
	
	public String getCheckCode() {
		return checkCode;
	}
	
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	public String getUserCellPhone() {
		return userCellPhone;
	}
	
	public void setUserCellPhone(String userCellPhone) {
		this.userCellPhone = userCellPhone;
	}
	
	public RedeemCodeStu getStatus() {
		return status;
	}
	
	public void setStatus(RedeemCodeStu status) {
		this.status = status;
	}
	
	public String getDeliverStoreNumber() {
		return deliverStoreNumber;
	}
	
	public void setDeliverStoreNumber(String deliverStoreNumber) {
		this.deliverStoreNumber = deliverStoreNumber;
	}
	
	public DateTime getDeliverDate() {
		return deliverDate;
	}
	
	public void setDeliverDate(DateTime deliverDate) {
		this.deliverDate = deliverDate;
	}
	
	public String getOperatorCellPhone() {
		return operatorCellPhone;
	}
	
	public void setOperatorCellPhone(String operatorCellPhone) {
		this.operatorCellPhone = operatorCellPhone;
	}
	
	public String getRedeemStoreNumber() {
		return RedeemStoreNumber;
	}
	
	public void setRedeemStoreNumber(String redeemStoreNumber) {
		RedeemStoreNumber = redeemStoreNumber;
	}
	
	public DateTime getRedeemDate() {
		return redeemDate;
	}
	
	public void setRedeemDate(DateTime redeemDate) {
		this.redeemDate = redeemDate;
	}
}
