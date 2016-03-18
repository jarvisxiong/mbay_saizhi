package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.joda.time.DateTime;

public class SMSSendOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2472622006490449772L;
	/**商户编号**/
	private String usernumber;
	
	/**订单编号*/
	private String orderNumber;
	
	/**对应的美贝价格**/
	private double mbayPrice;
	
	/**短信内容***/
	private String content;
	
	/**创建时间***/
	private DateTime createTime;

	
	
	public String getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getMbayPrice() {
		return mbayPrice;
	}

	public void setMbayPrice(double mbayPrice) {
		this.mbayPrice = mbayPrice;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	
}
