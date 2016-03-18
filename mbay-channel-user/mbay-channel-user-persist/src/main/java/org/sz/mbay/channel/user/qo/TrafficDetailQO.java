package org.sz.mbay.channel.user.qo;

import org.joda.time.DateTime;

/** 
* @Description:流量收支详情QO
* @author han.han 
* @date 2015-4-1 下午2:53:04 
*  
*/
public class TrafficDetailQO {
	
	private String userNumber;

	private String number;

	private DateTime startTime;

	private DateTime endTime;

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}	

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
	

}
