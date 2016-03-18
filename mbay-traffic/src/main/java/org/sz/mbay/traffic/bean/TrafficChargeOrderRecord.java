package org.sz.mbay.traffic.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import org.sz.mbay.traffic.util.ChargeOrderStatus;

@SuppressWarnings("serial")
public class TrafficChargeOrderRecord implements Serializable {
	/**唯一标识*/
	private long id;
	
	/**创建时间**/
	private Timestamp createtime;
	
	/**订单号***/
	private String ordernumber;
	
	/**操作人**/
	private  int userid;
	
	/**ip来源**/
	private String ip;
	
	/**内容详情**/
	private String content;
	
	/**当前订单状态***/
	private ChargeOrderStatus orderstatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getOrdernumber() {
		return ordernumber;
	}

	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ChargeOrderStatus getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(ChargeOrderStatus orderstatus) {
		this.orderstatus = orderstatus;
	}


}
