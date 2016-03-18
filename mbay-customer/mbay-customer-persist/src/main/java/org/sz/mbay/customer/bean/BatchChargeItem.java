package org.sz.mbay.customer.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import org.sz.mbay.customer.enums.BatchChargeMethod;

/**
 * 批充单次充值记录
 * 
 * @author ONECITY
 * 
 */
@SuppressWarnings("serial")
public class BatchChargeItem implements Serializable {

	/* 主键 */
	private int id;

	/* 批充信息id */
	private int batchid;

	/* 批充时间 */
	private Timestamp createtime;

	/*成功数量 */
	private int success;

	/* 失败数量 */
	private int fail;

	/* 数量 */
	private int num;
	
	/*
	 * 1.周期批充  2.手动批充
	 * 定时批充值 OR 手动批充
	 */
	private BatchChargeMethod chargemethod;
	/* 备注 */
	private String note;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBatchid() {
		return batchid;
	}

	public void setBatchid(int batchid) {
		this.batchid = batchid;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public BatchChargeMethod getChargemethod() {
		return chargemethod;
	}

	public void setChargemethod(BatchChargeMethod chargemethod) {
		this.chargemethod = chargemethod;
	}

	
	public String getNote() {
		return note;
	}

	
	public void setNote(String note) {
		this.note = note;
	}
}