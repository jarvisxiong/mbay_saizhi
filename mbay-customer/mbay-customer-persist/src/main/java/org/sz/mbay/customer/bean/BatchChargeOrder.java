package org.sz.mbay.customer.bean;

import java.io.Serializable;

/**
 * 
 * @Description:单次批充记录-流量订单信息关联表
 * @author frank.zong
 * @date 2015-2-5 下午4:53:41 
 *
 */
@SuppressWarnings("serial")
public class BatchChargeOrder implements Serializable {
	//单次批充记录id
	private int itemid;
	//流量订单number
	private String ordernumber;

	public int getItemid() {
		return itemid;
	}
	
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
}