package org.sz.mbay.channel.bean;

import org.sz.mbay.promotion.enums.RedeemCodeStu;

public class Product {
	
	private String activityId;
	private String redeemCode;
	private String checkCode;
	private String storeId;
	private String cellphone;
	private RedeemCodeStu codeStatus;
	
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
	
	public String getStoreId() {
		return storeId;
	}
	
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public String getActivityId() {
		return activityId;
	}
	
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	
	public RedeemCodeStu getCodeStatus() {
		return codeStatus;
	}
	
	public void setCodeStatus(RedeemCodeStu codeStatus) {
		this.codeStatus = codeStatus;
	}
}
