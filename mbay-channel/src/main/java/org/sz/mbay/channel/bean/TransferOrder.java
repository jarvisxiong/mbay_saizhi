package org.sz.mbay.channel.bean;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;
import org.sz.mbay.channel.enums.TransferOrderStauts;
import org.sz.mbay.channel.user.bean.User;

@SuppressWarnings("serial")
public class TransferOrder implements Serializable {
	
	/* 唯一标识 */
	private long orderid;
	
	/* 订单号 */
	private String ordernumber;
	
	/* 转账用户ID */
	private String usernumber;
	
	/* 转账金额 */
	@Min(value = 0)
	@Digits(integer = 10, fraction = 2)
	private double payAmount;
	
	/* 赠送金额 */
	@Min(value = 0)
	@Digits(integer = 10, fraction = 2)
	private double sendAmount;
	
	/* 转账说明 */
	@Size(max = 140)
	private String transfernote;
	
	/* 接受者 */
	private User optuser;
	
	/* 是否发送短信 */
	private int sendsms;
	
	/* 创建时间 */
	private DateTime createtime;
	/* 转账状态 */
	private TransferOrderStauts status;
	
	public TransferOrderStauts getStatus() {
		return status;
	}
	
	public void setStatus(TransferOrderStauts status) {
		this.status = status;
	}
	
	public String getOrdernumber() {
		return ordernumber;
	}
	
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	
	public int getSendsms() {
		return sendsms;
	}
	
	public void setSendsms(int sendsms) {
		this.sendsms = sendsms;
	}
	
	public User getOptuser() {
		return optuser;
	}
	
	public String getUsernumber() {
		return usernumber;
	}
	
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	
	public void setOptuser(User optuser) {
		this.optuser = optuser;
	}
	
	public long getOrderid() {
		return orderid;
	}
	
	public void setOrderid(long orderid) {
		this.orderid = orderid;
	}
	
	public double getPayAmount() {
		return payAmount;
	}
	
	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}
	
	public String getTransfernote() {
		return transfernote;
	}
	
	public void setTransfernote(String transfernote) {
		this.transfernote = transfernote;
	}
	
	public double getSendAmount() {
		return sendAmount;
	}
	
	public void setSendAmount(double sendAmount) {
		this.sendAmount = sendAmount;
	}
	
	public DateTime getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(DateTime createtime) {
		this.createtime = createtime;
	}
	
}
