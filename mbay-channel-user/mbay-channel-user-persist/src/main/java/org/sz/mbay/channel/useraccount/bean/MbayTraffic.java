package org.sz.mbay.channel.useraccount.bean;

import java.io.Serializable;

/** 
* @Description: 美贝数据流量
* @author han.han 
* @date 2015-3-26 下午12:25:29 
*  
*/
public class MbayTraffic implements Serializable {
	
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
	private int traffic;
	
	private int lockedTraffic;
	

	public int getLockedTraffic() {
		return lockedTraffic;
	}

	public void setLockedTraffic(int lockedTraffic) {
		this.lockedTraffic = lockedTraffic;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}
	
	

}
