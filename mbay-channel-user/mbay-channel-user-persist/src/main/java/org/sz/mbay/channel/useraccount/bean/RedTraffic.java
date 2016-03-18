package org.sz.mbay.channel.useraccount.bean;

import java.io.Serializable;

/** 
* @Description: 用户红包流量
* @author han.han 
* @date 2015-3-31 上午10:50:33 
*  
*/
public class RedTraffic implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2026836450693112072L;

	/**
	 * 用户编号
	 */
	private String userNumber;
	
	/**
	 * 流量大小 以兆为单位
	 */
	private double traffic;
	
	private double lockedTraffic;
	
	
	
	public double getLockedTraffic() {
		return lockedTraffic;
	}

	public void setLockedTraffic(double lockedTraffic) {
		this.lockedTraffic = lockedTraffic;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public double getTraffic() {
		return traffic;
	}

	public void setTraffic(double traffic) {
		this.traffic = traffic;
	}
	
	

}
