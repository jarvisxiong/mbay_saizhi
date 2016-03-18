package org.sz.mbay.trafficred.bean;

import org.sz.mbay.base.model.BaseEntityModel;

/**
 * 摇中美贝
 * 
 * @author jerry
 */
public class TrafficRedMbay extends BaseEntityModel {
	
	private static final long serialVersionUID = 7527948451040098617L;
	
	// 美贝大小
	private Integer mbay;
	
	public Integer getMbay() {
		return mbay;
	}
	
	public void setMbay(Integer mbay) {
		this.mbay = mbay;
	}
}
