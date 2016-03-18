package org.sz.mbay.channel.useraccount.bean;

import java.io.Serializable;

import org.joda.time.DateTime;

/** 
* @Description: 流量转账订单
* @author han.han 
* @date 2015-3-30 下午2:59:48 
*  
*/
public class RedTrafficTransferOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1080515798793066893L;
	
	/**
	 * 转账单号
	 */
	private String orderNumber;
	
	/**
	 * 转出方用户编号
	 */
	private String fromUserNumber;
	
	/**
	 * 转入方用户编号
	 */
	private String toUserNumber;
	
	/**
	 * 流量大小
	 */
	private int traffic;
	
	/**
	 *转账时间 
	 */
	private DateTime createTime;
	
	/**
	 * 转账说明
	 */
	private String remark;
	
	/**
	 * 是否发送短信
	 */
	private boolean sendSms;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getFromUserNumber() {
		return fromUserNumber;
	}

	public void setFromUserNumber(String fromUserNumber) {
		this.fromUserNumber = fromUserNumber;
	}

	public String getToUserNumber() {
		return toUserNumber;
	}

	public void setToUserNumber(String toUserNumber) {
		this.toUserNumber = toUserNumber;
	}

	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}	

	public String getRemark() {
		return remark;
	}
	

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isSendSms() {
		return sendSms;
	}

	public void setSendSms(boolean sendSms) {
		this.sendSms = sendSms;
	}
	
	

}
