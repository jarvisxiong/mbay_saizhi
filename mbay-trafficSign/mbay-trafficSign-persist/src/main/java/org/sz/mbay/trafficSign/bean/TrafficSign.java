package org.sz.mbay.trafficSign.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.trafficSign.enums.TrafficSignStatus;

/**
 * 签约实体bean表
 * 
 * @author Frank
 * 
 */
@SuppressWarnings("serial")
public class TrafficSign implements Serializable {
	
	/* id*** */
	private int id;
	/* 用户编号*** */
	private String usernumber;
	/* 签约时间*** */
	private String time;
	/* 网址*** */
	@NotBlank
	private String url;
	/* 联系人*** */
	@NotBlank
	private String person;
	/* 联系电话*** */
	@NotBlank
	private String phone;
	/* 状态 0->未审核,1->审核通过,2->审核不通过*** */
	private TrafficSignStatus status;
	/* 不通过原因*** */
	private String reason;
	/* 加密字段*** */
	private String pid;
	/* 是否启用 1->启用,2->禁用*** */
	private EnableState enable;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsernumber() {
		return usernumber;
	}
	
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getPerson() {
		return person;
	}
	
	public void setPerson(String person) {
		this.person = person;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public TrafficSignStatus getStatus() {
		return status;
	}
	
	public void setStatus(TrafficSignStatus status) {
		this.status = status;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public EnableState getEnable() {
		return enable;
	}
	
	public void setEnable(EnableState enable) {
		this.enable = enable;
	}
}
