package org.sz.mbay.particularcase.pingan.trafficred.bean;

import java.io.Serializable;
import org.joda.time.DateTime;
import org.sz.mbay.particularcase.pingan.trafficred.enums.MbayStatus;
import org.sz.mbay.particularcase.pingan.trafficred.enums.PingAnStatus;

public class PingAnRecord implements Serializable {
	
	private static final long serialVersionUID = -7564815761563110479L;
	
	private int id;
	
	private String mobile;
	
	private PingAnStatus pingAnStatus;
	
	private MbayStatus mbayStatus;
	
	private DateTime createTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public PingAnStatus getPingAnStatus() {
		return pingAnStatus;
	}
	
	public void setPingAnStatus(PingAnStatus pingAnStatus) {
		this.pingAnStatus = pingAnStatus;
	}
	
	public MbayStatus getMbayStatus() {
		return mbayStatus;
	}
	
	public void setMbayStatus(MbayStatus mbayStatus) {
		this.mbayStatus = mbayStatus;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
}
