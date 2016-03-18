package org.sz.mbay.trafficred.dto;

import java.io.Serializable;

public class ResultDTO implements Serializable {
	
	private static final long serialVersionUID = -841534437213614916L;
	
	public enum ResultType {
		TRAFFIC, MBAY
	}
	
	// 活动编号
	private String cNumber;
	
	// 手机号码
	private String mobile;
	
	// 兑换记录id（pbe加密）
	private String recordId;
	
	// 奖品大小
	private Integer size;
	
	// 类型
	private ResultType type;
	
	// 钱包交易流水号
	private String serialNumber;
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public ResultType getType() {
		return type;
	}
	
	public void setType(ResultType type) {
		this.type = type;
	}
	
	public String getRecordId() {
		return recordId;
	}
	
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getcNumber() {
		return cNumber;
	}
	
	public void setcNumber(String cNumber) {
		this.cNumber = cNumber;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
