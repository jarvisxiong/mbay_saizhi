package org.sz.mbay.trafficorder.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderStatus;

/**
 * @Description: 流量订单充值记录
 * @author han.han
 * @date 2015-01-20 上午9:35:09
 * 		
 */
public class TrafficOrderRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1776855038823347543L;
	
	/** 创建时间 **/
	private DateTime createTime;
	
	/** 订单号 ***/
	private String orderNumber;
	
	/** ip来源 **/
	private String IP;
	
	/** 内容详情 **/
	private String content;
	
	/** 当前订单状态 ***/
	private TrafficOrderStatus status;
	
	/** 运营商充值状态 ** */
	private OperatorRechargeStatus ors;
	
	// 操作人
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getIP() {
		return IP;
	}
	
	public void setIP(String iP) {
		IP = iP;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public TrafficOrderStatus getStatus() {
		return status;
	}
	
	public void setStatus(TrafficOrderStatus status) {
		this.status = status;
	}
	
	public OperatorRechargeStatus getOrs() {
		return ors;
	}
	
	public void setOrs(OperatorRechargeStatus ors) {
		this.ors = ors;
	}
	
}
