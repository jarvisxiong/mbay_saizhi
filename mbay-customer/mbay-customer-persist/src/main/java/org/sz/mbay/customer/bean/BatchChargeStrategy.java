package org.sz.mbay.customer.bean;

import java.io.Serializable;

import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;

/**
 * 
 * @Description:批充基本信息-流量包信息关联表
 * @author frank.zong
 * @date 2015-2-5 下午4:53:41 
 *
 */
@SuppressWarnings("serial")
public class BatchChargeStrategy implements Serializable {
	//批充id
	private int batchid;
	//号码所归属运营商(移动/联通/电信)
	private OperatorType operator;
	//流量包
	private TrafficPackage trafficpackage;
	
	public TrafficPackage getTrafficpackage() {
		return trafficpackage;
	}

	public void setTrafficpackage(TrafficPackage trafficpackage) {
		this.trafficpackage = trafficpackage;
	}

	public int getBatchid() {
		return batchid;
	}

	public void setBatchid(int batchid) {
		this.batchid = batchid;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public void setOperator(OperatorType operator) {
		this.operator = operator;
	}
}