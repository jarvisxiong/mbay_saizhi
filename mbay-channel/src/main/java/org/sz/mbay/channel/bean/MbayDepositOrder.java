package org.sz.mbay.channel.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.sz.mbay.channel.enums.DepositState;

/**
 * @Description: 美贝预存订单记录
 * @author han.han
 * @date 2014-9-18 下午8:49:36 
 *
 */
public class MbayDepositOrder implements Serializable{

	private static final long serialVersionUID = 1L;

	/*美贝预存流水号***/
	private String depositNumber;
	
	/*用户编号***/
	private String usernumber;
	
	/*美贝数量***/
	private BigDecimal  mbayQuantity;
	
	/*支付价格**/
	private BigDecimal  price;
	
	/*创建时间**/
	private DateTime createtime;
	
	/*****/
	private DepositState state;

	public String getDepositNumber() {
		return depositNumber;
	}

	public void setDepositNumber(String depositNumber) {
		this.depositNumber = depositNumber;
	}

	public String getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}

	public BigDecimal getMbayQuantity() {
		return mbayQuantity;
	}

	public void setMbayQuantity(BigDecimal mbayQuantity) {
		this.mbayQuantity = mbayQuantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	

	public DateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(DateTime createtime) {
		this.createtime = createtime;
	}

	public DepositState getState() {
		return state;
	}

	public void setState(DepositState state) {
		this.state = state;
	}
	
	

}
