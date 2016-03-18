package org.sz.mbay.trafficred.session;

import java.io.Serializable;

public class SessionUser implements Serializable {
	
	private static final long serialVersionUID = -8541973918977535385L;
	
	// 账号
	private String mobile;
	
	// 用户编号
	private String userNumber;
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
}
