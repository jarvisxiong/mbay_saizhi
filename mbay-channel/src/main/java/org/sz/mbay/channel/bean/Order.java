package org.sz.mbay.channel.bean;

import org.sz.mbay.channel.enums.OrderStatus;

public class Order extends BaseEntityModel {

	private static final long serialVersionUID = 8755610666550372506L;
	/** 订单序列号 **/
	private String number;
	/** 订单用户编号 **/
	private String userNumber;
	/** 订单状态 **/
	private OrderStatus status;
	/** 订单价格 **/
	private double price;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
