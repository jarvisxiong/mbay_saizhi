package org.sz.mbay.trafficred.bean;

import org.sz.mbay.base.model.BaseEntityModel;

/**
 * 流量红包 MB赠送领取限制
 * 
 * @author han.han
 */
public class TrafficRedMbayGiftLimitConfig extends BaseEntityModel {
	
	private static final long serialVersionUID = -6509490042955067988L;
	
	// 数据库没有配置时的默认值
	public static final TrafficRedMbayGiftLimitConfig DEFAULT = new TrafficRedMbayGiftLimitConfig();
	
	static {
		DEFAULT.setDayLimit(3);
		DEFAULT.setWeekLimit(5);
		DEFAULT.setMonthLimit(10);
		DEFAULT.setLimit(true);
		DEFAULT.setValidity(72);
	}
	
	// 是否限制
	private Boolean limit;
	
	// 月限制
	private Integer monthLimit;
	
	// 周限制
	private Integer weekLimit;
	
	// 日限制
	private Integer dayLimit;
	
	// 链接有效时长（小时）
	private Integer validity;
	
	public Boolean getLimit() {
		return limit;
	}
	
	public void setLimit(Boolean limit) {
		this.limit = limit;
	}
	
	public Integer getMonthLimit() {
		return monthLimit;
	}
	
	public void setMonthLimit(Integer monthLimit) {
		this.monthLimit = monthLimit;
	}
	
	public Integer getWeekLimit() {
		return weekLimit;
	}
	
	public void setWeekLimit(Integer weekLimit) {
		this.weekLimit = weekLimit;
	}
	
	public Integer getDayLimit() {
		return dayLimit;
	}
	
	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}
	
	public Integer getValidity() {
		return validity;
	}
	
	public void setValidity(Integer validity) {
		this.validity = validity;
	}
	
}
