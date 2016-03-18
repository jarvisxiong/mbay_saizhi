package org.sz.mbay.channel.bean;

import java.io.Serializable;

/**
 * 流米预付总额
 * @author frank.zong
 */
public class LiuMiTotalPrice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int id;
	
	/**
	 * 总金额
	 */
	private double price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}