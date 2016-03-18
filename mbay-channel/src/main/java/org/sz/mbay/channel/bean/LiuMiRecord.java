package org.sz.mbay.channel.bean;

import java.io.Serializable;

/**
 * 流米充值记录
 * @author frank.zong
 */
public class LiuMiRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int id;
	
	/**
	 * 流米订单号
	 */
	private String orderNo;
	
	/**
	 * 我方订单号
	 */
	private String extNo;
	/**
	 * 时间
	 */
	private String createtime;
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 流量大小
	 */
	private int traffic;
	
	/**
	 * 折扣价格
	 */
	private double price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public int getTraffic() {
		return traffic;
	}
	
	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getExtNo() {
		return extNo;
	}

	public void setExtNo(String extNo) {
		this.extNo = extNo;
	}
}