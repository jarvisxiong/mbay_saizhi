package org.sz.mbay.trafficred.bean;

import org.joda.time.DateTime;
import org.sz.mbay.base.model.BaseEntityModel;

/**
 * 抽奖
 * 
 * @author frank.zong
 */
public class TrafficRedRaffle extends BaseEntityModel {
	
	private static final long serialVersionUID = 7527948451040098617L;
	
	// ip
	private String ip;
	
	// 有效期
	private DateTime validTime;
	
	// 手机号
	private String mobile;
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public DateTime getValidTime() {
		return validTime;
	}
	
	public void setValidTime(DateTime validTime) {
		this.validTime = validTime;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
