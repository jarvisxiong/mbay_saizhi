package org.sz.mbay.customer.qo;

import org.sz.mbay.base.qo.BaseForm;

public class BatchChargeMobileForm extends BaseForm {
	
	private int batchid;
	
	private String mobile;
	
	private String usernumber;

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

	public String getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
}