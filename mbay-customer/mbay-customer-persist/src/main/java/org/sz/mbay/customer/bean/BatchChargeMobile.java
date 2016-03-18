package org.sz.mbay.customer.bean;

import java.io.Serializable;

import org.sz.mbay.operator.enums.OperatorType;

/**
 * 
 * @Description:批充手机号码信息
 * @author han.han
 * @date 2014-8-27 下午7:53:41 
 *
 */
@SuppressWarnings("serial")
public class BatchChargeMobile implements Serializable {
	
	//主键
	private int batchid;
	//充值号码
	private String mobile;	
	//号码所归属运营商(移动/联通/电信)
	private OperatorType operator;
	
	public int getBatchid() {
		return batchid;
	}

	public void setBatchid(int batchid) {
		this.batchid = batchid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public OperatorType getOperator() {
		return operator;
	}

	public void setOperator(OperatorType operator) {
		this.operator = operator;
	}
}