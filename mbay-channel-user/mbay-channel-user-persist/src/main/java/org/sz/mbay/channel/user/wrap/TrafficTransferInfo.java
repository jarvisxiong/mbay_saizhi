package org.sz.mbay.channel.user.wrap;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;


public class TrafficTransferInfo {
	
	/**
	 * 转出方用户编号
	 */
	private String fromUserNumber;
	
	/**
	 * 转入方用户编号
	 */
	@NotNull
	@Size(max=8,min=8)
	private String toUserNumber;
	
	/**
	 * 流量大小
	 */
	private Integer traffic;
	
	
	/**
	 * 转账说明
	 */
	@Size(max=140)
	private String remark;
	
	/**
	 * 是否发送短信
	 */
	private boolean sendSms;
	
	
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

	@Min(value=1)  
	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}	

	public boolean isSendSms() {
		return sendSms;
	}

	public void setSendSms(boolean sendSms) {
		this.sendSms = sendSms;
	}

	public String getRemark() {
		if(!StringUtils.isEmpty(remark)){
			return "-"+remark;
		}
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTraffic(Integer traffic) {
		this.traffic = traffic;
	}

	
	

}
