package org.sz.mbay.mall.bean;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * 优惠券券码
 * 
 * @author frank.zong
 */
public class MallCouponTicket implements Serializable {
	
	private static final long serialVersionUID = 1255654747758656498L;
	
	/** id **/
	private int id;
	
	/** 兑换项编码 **/
	private String itemNumber;
	
	/** 创建时间 **/
	private DateTime createTime;
	
	/** 开始日期 **/
	private DateTime startTime;
	
	/** 结束日期 **/
	private DateTime endTime;
	
	/** 券码 **/
	private String ticket;
	
	/** 密码 **/
	private String password;
	
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
	
	public String getItemNumber() {
		return itemNumber;
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public String getTicket() {
		return ticket;
	}
	
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
