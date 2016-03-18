package org.sz.mbay.channel.bean;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 转账记录表  
 * @author meibei-hrain
 *
 */
@SuppressWarnings("serial")
public class TransferRecord implements Serializable {

	private long id;

	/*流水号***/
	private String number;
	/* 转入Id  */
	private int inuserid;
	/*   */
	private int outuserid;
	/* 转出Id  */
	private Timestamp createtime;
	/*  支付时间 */
	private Timestamp paytime;
	/* 转账金额  */
	private double payAmount;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getInuserid() {
		return inuserid;
	}

	public void setInuserid(int inuserid) {
		this.inuserid = inuserid;
	}

	public int getOutuserid() {
		return outuserid;
	}

	public void setOutuserid(int outuserid) {
		this.outuserid = outuserid;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Timestamp getPaytime() {
		return paytime;
	}

	public void setPaytime(Timestamp paytime) {
		this.paytime = paytime;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}



}
