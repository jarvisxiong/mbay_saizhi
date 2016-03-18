package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.channel.user.bean.User;

/**
 * @author Tom
 * @version 创建时间：2014-10-14下午6:10:40
 * @type 类型描述 财务拨款记录
 */
@SuppressWarnings("serial")
public class AppropriationOrder implements Serializable {
	
	/* 标示主键id */
	private int id;
	
	/* 订单号 */
	private String ordernumber;
	
	/* 市场账户 */
	private User marketUser;
	
	/* 转入美贝数量 */
	private double mbaynum;
	
	/* 转账时间 */
	private DateTime transfertime;
	
	/* 操作人 */
	private String operatorperson;
	
	/* 转账备注 */
	private String remark;
	
	/* 是否转入成功 */
	private int state;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getMbaynum() {
		return mbaynum;
	}
	
	public void setMbaynum(double mbaynum) {
		this.mbaynum = mbaynum;
	}
	
	public DateTime getTransfertime() {
		return transfertime;
	}
	
	public void setTransfertime(DateTime transfertime) {
		this.transfertime = transfertime;
	}
	
	public String getOperatorperson() {
		return operatorperson;
	}
	
	public void setOperatorperson(String operatorperson) {
		this.operatorperson = operatorperson;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public String getOrdernumber() {
		return ordernumber;
	}
	
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	
	public User getMarketUser() {
		return marketUser;
	}
	
	public void setMarketUser(User marketUser) {
		this.marketUser = marketUser;
	}
}
