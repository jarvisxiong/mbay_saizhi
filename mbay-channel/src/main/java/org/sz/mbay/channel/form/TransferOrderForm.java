package org.sz.mbay.channel.form;

import java.io.Serializable;

import org.sz.mbay.base.qo.BaseForm;

/**
 *@author Tom
 *@version 创建时间：2014-8-15下午12:29:52
 *@type 类型描述  public class TransferOrderForm{ }
 */
@SuppressWarnings("serial")
public class TransferOrderForm extends BaseForm implements Serializable {

    /**订单号号*/
    private String ordernumber;
    
    private String userNumber;

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
    
	
    
}
