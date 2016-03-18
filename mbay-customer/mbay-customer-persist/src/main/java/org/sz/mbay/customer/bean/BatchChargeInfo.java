package org.sz.mbay.customer.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.sz.mbay.customer.enums.BatchChargeMethod;

/**
 * 
 * @Description:批充信息详情表
 * @author han.han
 * @date 2014-8-27 下午7:53:41 
 *
 */
@SuppressWarnings("serial")
public class BatchChargeInfo implements Serializable {
	/* 主键 */
	private int id;
	/* 名字 */
	private String name;

	/* 渠道ID */
	private String usernumber;

	/* 已充次数 */
	private int chargetimes;

	/* 定时 */
	private int regulartime;

	/*
	 * 1.周期批充  2.手动批充
	 * 定时批充值 OR 手动批充
	 */
	private BatchChargeMethod chargemethod;
	
	/* 创建时间*/
	private Timestamp createtime;

	/* 美贝成本*/
	private double costmbay;

	/* 手机号码数量*/
	private int mobilenum;
	
	/*批量充值手机信息*/
	private List<BatchChargeMobile> batchchargemobiles;
	
	/* 批充流量包*/
	private List<BatchChargeStrategy> strategys;
	
	public List<BatchChargeMobile> getBatchchargemobiles() {
		return batchchargemobiles;
	}

	public void setBatchchargemobiles(List<BatchChargeMobile> batchchargemobiles) {
		this.batchchargemobiles = batchchargemobiles;
	}
	
	public double getCostmbay() {
		return costmbay;
	}

	public void setCostmbay(double costmbay) {
		this.costmbay = costmbay;
	}

	public int getMobilenum() {
		return mobilenum;
	}

	public void setMobilenum(int mobilenum) {
		this.mobilenum = mobilenum;
	}
	
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public int getRegulartime() {
		return regulartime;
	}

	public void setRegulartime(int regulartime) {
		this.regulartime = regulartime;
	}

	public BatchChargeMethod getChargemethod() {
		return chargemethod;
	}

	public void setChargemethod(BatchChargeMethod chargemethod) {
		this.chargemethod = chargemethod;
	}

	public int getChargetimes() {
		return chargetimes;
	}

	public void setChargetimes(int chargetimes) {
		this.chargetimes = chargetimes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}

	public List<BatchChargeStrategy> getStrategys() {
		return strategys;
	}
	
	public void setStrategys(List<BatchChargeStrategy> strategys) {
		this.strategys = strategys;
	}
}