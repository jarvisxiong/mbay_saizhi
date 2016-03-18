package org.sz.mbay.trafficorder.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.trafficorder.enums.TrafficOrderRefundStatus;

/**
 * 流量充值订单退款
 * 
 * @author jerry
 */
public class TrafficOrderRefund implements Serializable {
	
	private static final long serialVersionUID = 4170145775528846476L;
	
	// 订单编号
	private String orderNumber;
	
	// 退款状态
	private TrafficOrderRefundStatus status;
	
	// 失败原因
	private String reason;
	
	// 价格
	private Double mbayPrice;
	
	// 退款日期
	private DateTime createTime;
	
	// 关联的订单记录
	private TrafficOrder order;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public TrafficOrderRefundStatus getStatus() {
		return status;
	}
	
	public void setStatus(TrafficOrderRefundStatus status) {
		this.status = status;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Double getMbayPrice() {
		return mbayPrice;
	}
	
	public void setMbayPrice(Double mbayPrice) {
		this.mbayPrice = mbayPrice;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public TrafficOrder getOrder() {
		return order;
	}
	
	public void setOrder(TrafficOrder order) {
		this.order = order;
	}
	
}
