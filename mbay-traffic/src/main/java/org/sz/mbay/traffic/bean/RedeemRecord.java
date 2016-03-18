package org.sz.mbay.traffic.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author ONECITY
 *
 */
@SuppressWarnings("serial")
public class RedeemRecord implements Serializable {
	public static final int  REDEEMING=1;
	public static final int  SUCCESS=2;
	public static final int  FAILD=3;
	
	/**ID***/
	private long id;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 兑换时间
	 */
	private Timestamp createtime;
	/**
	 * 对应用户ID
	 */
	private int userid;
	/**
	 * 对换手机号
	 */
	private String mobile;
	/**
	 * 兑换流量
	 */
	private int traffic;
	/**
	 * 兑换类型
	 */
	private int redeemtype;
	
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}

	public int getRedeemtype() {
		return redeemtype;
	}

	public void setRedeemtype(int redeemtype) {
		this.redeemtype = redeemtype;
	}

}
