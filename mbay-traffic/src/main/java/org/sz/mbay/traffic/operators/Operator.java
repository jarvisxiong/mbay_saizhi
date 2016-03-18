package org.sz.mbay.traffic.operators;

import java.util.List;

import org.sz.mbay.operator.enums.TrafficType;

public class Operator {   
	
	public Operator(String name,List<TrafficType> flowtypes,String registerclass){
		this.name=name;
		this.flowtypes=flowtypes;
		this.registerclass=registerclass;
	}
	
	/**
	 * 运营商名称
	 */
	private String name;
	
	/**
	 * 包含的流量类型
	 */
	private List<TrafficType> flowtypes;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TrafficType> getFlowtypes() {
		return flowtypes;
	}

	public void setFlowtypes(List<TrafficType> flowtypes) {
		this.flowtypes = flowtypes;
	}

	/**
	 * 对接接口注册类
	 */
	private String registerclass;

	public String getRegisterclass() {
		return registerclass;
	}

	public void setRegisterclass(String registerclass) {
		this.registerclass = registerclass;
	}

}
