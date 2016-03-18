package org.sz.mbay.traffic.bean;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class Trafficcard implements Serializable {
	public static final int NOTUSED=1;
	public static final int USED=2;
	public static final int USEING=3;
	public static final int UNABLEUSE=4;
	
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * 状态：1.未使用，2，已使用，3，使用中，4.无法使用
	 */
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 卡号
	 */
	private String cardnum;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 面值
	 */
	private String money;
	
	/**
	 * 流量
	 */
	private int traffic;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 通信运营商流量业务ID
	 */
	private int trafficbizid;
	
	/**过期时间*/
	private Timestamp expiretime;

	public Timestamp getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(Timestamp expiretime) {
		this.expiretime = expiretime;
	}

	public int getTrafficbizid() {
		return trafficbizid;
	}

	public void setTrafficbizid(int trafficbizid) {
		this.trafficbizid = trafficbizid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

}
