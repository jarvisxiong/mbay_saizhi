package org.sz.mbay.channel.useraccount.bean;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.channel.user.bean.User;

@SuppressWarnings("serial")
public class MbayTransferOrder extends BaseEntityModel  implements Serializable  {
	

	/* 订单号 */
	private String orderNumber;

	/* 转账用户ID */
	private String fromUserNumber;

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
	private String transferNote;

	/* 接受者 */
	private User toUser;

	/* 是否发送短信 */
	private int sendsms;

	/* 创建时间 */
	private DateTime createTime;


	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public int getSendsms() {
		return sendsms;
	}

	public void setSendsms(int sendsms) {
		this.sendsms = sendsms;
	}
	

	public String getFromUserNumber() {
		return fromUserNumber;
	}

	public void setFromUserNumber(String fromUserNumber) {
		this.fromUserNumber = fromUserNumber;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(double payAmount) {
		this.payAmount = payAmount;
	}	

	public String getTransferNote() {
		return transferNote;
	}

	public void setTransferNote(String transferNote) {
		this.transferNote = transferNote;
	}

	public double getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(double sendAmount) {
		this.sendAmount = sendAmount;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	
	
}
