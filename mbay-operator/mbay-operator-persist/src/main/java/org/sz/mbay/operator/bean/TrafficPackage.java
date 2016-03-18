package org.sz.mbay.operator.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.operator.enums.TrafficType;



/**
 * 流量包表
 * @author ONECITY
 * 流量包实体bean
 *
 */
@SuppressWarnings("serial")
public class TrafficPackage implements Serializable {
	/** 关联运营商****/
	private Operator operator;
	
	/** 唯一标识**/
	private int id;
	
	/** 运营商唯一标识*/
	private int operatorid;
	
	/** 流量大小*/
	private int traffic;
	
	/** 美贝价格***/
	private double mbayprice;
	
	/** 创建时间*****/
	private DateTime createtime;
	
	/** 流量类型，国内or省内*/
	private TrafficType trafficType;
	
	/** 是否启用*/
	private EnableState isopen;
	
	/** 流量包编号*/
	private String packageCode;
	
	/**运营商方流量包编码*/
	private String operatorPackageCode;
	
	private double cost;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(int operatorid) {
		this.operatorid = operatorid;
	}

	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}
	

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public double getMbayprice() {
		return mbayprice;
	}

	public void setMbayprice(double mbayprice) {
		this.mbayprice = mbayprice;
	}

	

	public DateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(DateTime createtime) {
		this.createtime = createtime;
	}

	public TrafficType getTrafficType() {
		return trafficType;
	}

	public void setTrafficType(TrafficType trafficType) {
		this.trafficType = trafficType;
	}

	public EnableState getIsopen() {
		return isopen;
	}

	public void setIsopen(EnableState isopen) {
		this.isopen = isopen;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	
	public double getCost() {
		return cost;
	}

	
	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getOperatorPackageCode() {
		return operatorPackageCode;
	}

	public void setOperatorPackageCode(String operatorPackageCode) {
		this.operatorPackageCode = operatorPackageCode;
	}


}
