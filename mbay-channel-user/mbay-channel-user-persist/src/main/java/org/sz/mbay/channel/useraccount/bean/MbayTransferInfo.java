package org.sz.mbay.channel.useraccount.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang.StringUtils;

public class MbayTransferInfo {
	
	/**
	 * 转出方用户编号
	 */
	private String fromUserNumber;
	
	/**
	 * 转入方用户编号
	 */
	@NotNull
	@Size(max = 8, min = 8)
	private String toUserNumber;
	
	/**
	 * 转账金额
	 */
	private double mbayAmount;
	
	/**
	 * 赠送金额
	 */
	private double sendAmount;
	
	/**
	 * 转账说明
	 */
	@Size(max = 140)
	private String remark;
	
	public String getFromUserNumber() {
		return fromUserNumber;
	}
	
	
	public void setFromUserNumber(String fromUserNumber) {
		this.fromUserNumber = fromUserNumber;
	}
	
	public String getToUserNumber() {
		return toUserNumber;
	}
	
	public void setToUserNumber(String toUserNumber) {
		this.toUserNumber = toUserNumber;
	}
	
	public String getRemark() {
		if(!StringUtils.isEmpty(remark)){
			return remark;
		}
		return "";
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public double getMbayAmount() {
		return mbayAmount;
	}
	
	public void setMbayAmount(double mbayAmount) {
		this.mbayAmount = mbayAmount;
	}
	
	public double getSendAmount() {
		return sendAmount;
	}
	
	public void setSendAmount(double sendAmount) {
		this.sendAmount = sendAmount;
	}
	
}
