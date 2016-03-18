package org.sz.mbay.operator.bean;

import java.io.Serializable;

import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.operator.bean.Operator;

/**
 * 运营商流量业务，主要分国内流量，省内流量
 * 
 * @author ONECITY
 * 
 */
@SuppressWarnings("serial")
public class Trafficbusiness implements Serializable {
	
	public static final int STATE = 1, PROVINCE = 2;
	
	private int id;
	
	/**
	 * 对应运营商
	 */
	private Operator operator;
	
	/**
	 * 运营商ID
	 */
	private int operatorid;
	
	/**
	 * 省内各，国内流量
	 */
	private int traffictype;
	
	/**
	 * 最小流量包
	 */
	private int leasttraffic;
	
	/**
	 * 单价
	 */
	private double unitprice;
	
	/**
	 * 启用状态
	 */
	private EnableState enable;
	
	public EnableState getEnable() {
		return enable;
	}
	
	public void setEnable(EnableState enable) {
		this.enable = enable;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public String getTraffictypestr() {
		switch (this.traffictype) {
			case STATE:
				return "国内流量";
			case PROVINCE:
				return "省内流量";
		}
		return "未知";
	}
	
	public int getOperatorid() {
		return operatorid;
	}
	
	public void setOperatorid(int operatorid) {
		this.operatorid = operatorid;
	}
	
	public int getTraffictype() {
		return traffictype;
	}
	
	public void setTraffictype(int traffictype) {
		this.traffictype = traffictype;
	}
	
	public int getLeasttraffic() {
		return leasttraffic;
	}
	
	public void setLeasttraffic(int leasttraffic) {
		this.leasttraffic = leasttraffic;
	}
	
	public double getUnitprice() {
		return unitprice;
	}
	
	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}
	
}
