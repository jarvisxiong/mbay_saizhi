package org.sz.mbay.mall.bean;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * 额外兑换限制
 * 
 * @author frank.zong
 */
public class MallExtendLimit implements Serializable {
	
	private static final long serialVersionUID = 1255654747758656498L;
	
	/** 兑换项编码 **/
	private String itemNumber;
	
	/** 每日兑换限制(每天最多可被兑换的次数，不填则不做限制) **/
	private String dayLimit;
	
	/** 开始日期 **/
	private DateTime startTime;
	
	/** 结束日期 **/
	private DateTime endTime;
	
	public String getDayLimit() {
		return dayLimit;
	}
	
	public void setDayLimit(String dayLimit) {
		this.dayLimit = dayLimit;
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
	
	public String getItemNumber() {
		return itemNumber;
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
}
