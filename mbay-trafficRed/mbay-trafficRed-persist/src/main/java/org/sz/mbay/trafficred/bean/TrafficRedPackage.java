package org.sz.mbay.trafficred.bean;

import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.operator.enums.OperatorType;

/**
 * 流量红包
 * 
 * @author jerry
 */
public class TrafficRedPackage extends BaseEntityModel {
	
	private static final long serialVersionUID = 2118584798748201089L;
	
	// 流量包id
	private Integer packageId;
	
	// 关联的流量包对象
	private TrafficPackage trafficPackage;
	
	// 流量大小
	private Integer traffic;
	
	// 运营商类别
	private OperatorType operatorType;
	
	public Integer getPackageId() {
		return packageId;
	}
	
	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}
	
	public Integer getTraffic() {
		return traffic;
	}
	
	public void setTraffic(Integer traffic) {
		this.traffic = traffic;
	}
	
	public OperatorType getOperatorType() {
		return operatorType;
	}
	
	public void setOperatorType(OperatorType operatorType) {
		this.operatorType = operatorType;
	}
	
	public TrafficPackage getTrafficPackage() {
		return trafficPackage;
	}
	
	public void setTrafficPackage(TrafficPackage trafficPackage) {
		this.trafficPackage = trafficPackage;
	}
	
}
