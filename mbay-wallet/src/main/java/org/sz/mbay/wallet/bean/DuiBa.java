package org.sz.mbay.wallet.bean;

import org.joda.time.DateTime;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.wallet.enums.DuiBaState;

public class DuiBa extends BaseEntityModel {
	
	private static final long serialVersionUID = -3029225551308570657L;
	
	// 兑吧编号
	private String orderNumber;
	
	// 交易记录编号
	private String serialNumber;
	
	// 处理状态
	private DuiBaState state;
	
	// 创建时间
	private DateTime createTime;
	
	// 上城id
	private Integer mallId;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public DuiBaState getState() {
		return state;
	}
	
	public void setState(DuiBaState state) {
		this.state = state;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public Integer getMallId() {
		return mallId;
	}
	
	public void setMallId(Integer mallId) {
		this.mallId = mallId;
	}
	
}
