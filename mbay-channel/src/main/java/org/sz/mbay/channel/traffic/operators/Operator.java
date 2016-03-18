package org.sz.mbay.channel.traffic.operators;

import java.util.List;

import org.sz.mbay.operator.enums.TrafficType;

public class Operator {   
	
	
	
	public Operator(String name,List<TrafficType> flowtypes){
		this.name=name;
		this.flowtypes=flowtypes;
	}
	
	/**
	 * 运营商名称
	 */
	private String name;
	
	/**
	 * 包含的流量类型
	 */
	private List<TrafficType> flowtypes;
	
	public List<TrafficType> getFlowtypes() {
		return flowtypes;
	}

	public void setFlowtypes(List<TrafficType> flowtypes) {
		this.flowtypes = flowtypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
