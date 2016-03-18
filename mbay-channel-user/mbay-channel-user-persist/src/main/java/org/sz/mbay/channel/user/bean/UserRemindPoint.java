package org.sz.mbay.channel.user.bean;

import java.io.Serializable;

import javax.validation.constraints.Min;

/**
 * 
 * @ClassName: UserRemindPoint
 * 
 * @Description: 用户提醒阀值类
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2014年12月5日 下午2:57:30
 * 
 */
public class UserRemindPoint implements Serializable {
	
	private static final long serialVersionUID = -7446514999513414196L;
	/** 用户编号 **/
	private String usernumber;
	/** 短信提醒阀值 **/
	@Min(value = 1)
	private int smsRemindPoint;
	/** mbay提醒阀值 **/
	@Min(value = 1)
	private int mbayRemindPoint;
	/** 是否已经发送美贝余额不足通知 **/
	private boolean mbay_sent;
	/** 是否已经发送短信余额不足通知 **/
	private boolean sms_sent;
	
	public String getUsernumber() {
		return usernumber;
	}
	
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	
	public int getSmsRemindPoint() {
		return smsRemindPoint;
	}
	
	public void setSmsRemindPoint(int smsRemindPoint) {
		this.smsRemindPoint = smsRemindPoint;
	}
	
	public int getMbayRemindPoint() {
		return mbayRemindPoint;
	}
	
	public void setMbayRemindPoint(int mbayRemindPoint) {
		this.mbayRemindPoint = mbayRemindPoint;
	}
	
	public boolean isMbay_sent() {
		return mbay_sent;
	}
	
	public void setMbay_sent(boolean mbay_sent) {
		this.mbay_sent = mbay_sent;
	}
	
	public boolean isSms_sent() {
		return sms_sent;
	}
	
	public void setSms_sent(boolean sms_sent) {
		this.sms_sent = sms_sent;
	}
	
}
