package org.sz.mbay.channel.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 流量充值订单
 * @author ONECITY
 *
 */
@SuppressWarnings("serial")
public class ChargeOrder implements Serializable {

	/**主键ID**/
	private int id;
	
	/**订单编号**/
	private String number;
	
	/**创建时间**/
	private Timestamp createtime;
	
	/**描述**/
	private String info;
	
	/**状态**/
	private int state;
	
	/**金额**/
	private double amount;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	

}
