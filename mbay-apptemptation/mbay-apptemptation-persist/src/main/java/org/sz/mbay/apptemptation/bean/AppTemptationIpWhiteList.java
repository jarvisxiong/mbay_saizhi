package org.sz.mbay.apptemptation.bean;

import org.joda.time.DateTime;
import org.sz.mbay.base.model.BaseEntityModel;

/**
 * IP白名单
 * 
 * 此ip验证通过则不会再验证真实ip
 *
 * @author jerry
 */
public class AppTemptationIpWhiteList extends BaseEntityModel {
	
	private static final long serialVersionUID = 7953906239670753090L;
	
	// 商户编号
	private String userNumber;
	
	// 协商ip
	private String ip;
	
	// 创建时间
	private DateTime createTime;
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
}
