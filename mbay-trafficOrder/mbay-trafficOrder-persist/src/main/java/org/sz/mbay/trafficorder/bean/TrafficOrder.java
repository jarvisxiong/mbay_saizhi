package org.sz.mbay.trafficorder.bean;

import org.joda.time.DateTime;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderType;

import net.sf.json.JSONObject;

public class TrafficOrder extends BaseEntityModel {

	private static final long serialVersionUID = 6919669797721025253L;
	/** 订单号 * */
	private String number;

	/** 运营商编号 **/
	private String operatorNumber;

	/**
	 * 创建时间
	 */
	private DateTime createTime;

	/** 商户编号 */
	private String userNumber;

	/** 对换手机号 */
	private String mobile;

	/** 美贝价格 * */
	private double mbayPrice;

	/** 充值结果 * */
	private TrafficOrderStatus status;

	/** 运营商充值状态 ** */
	private OperatorRechargeStatus ors;

	/** 描述信息 ** */
	private String orderName;

	/**
	 * 充值流量包
	 */
	private TrafficPackage trafficPackage;

	/**
	 * 实际路由走的流量包
	 */
	private int routePackageid;

	/** 关联编号 ** */
	private String relateNumber;

	/** 订单类型 **/
	private TrafficOrderType orderType;

	// 退款
	private TrafficOrderRefund refund;

	public TrafficOrderRefund getRefund() {
		return refund;
	}

	public void setRefund(TrafficOrderRefund refund) {
		this.refund = refund;
	}

	public TrafficPackage getTrafficPackage() {
		return trafficPackage;
	}

	public void setTrafficPackage(TrafficPackage trafficPackage) {
		this.trafficPackage = trafficPackage;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public double getMbayPrice() {
		return mbayPrice;
	}

	public void setMbayPrice(double mbayPrice) {
		this.mbayPrice = mbayPrice;
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

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getRoutePackageid() {
		return routePackageid;
	}

	public void setRoutePackageid(int routePackageid) {
		this.routePackageid = routePackageid;
	}

	public String getRelateNumber() {
		return relateNumber;
	}

	public void setRelateNumber(String relateNumber) {
		this.relateNumber = relateNumber;
	}

	public TrafficOrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(TrafficOrderType orderType) {
		this.orderType = orderType;
	}

	public String getOperatorNumber() {
		return operatorNumber;
	}

	public void setOperatorNumber(String operatorNumber) {
		this.operatorNumber = operatorNumber;
	}

	@Override
	public String toString() {
		return new JSONObject().element("number", this.getNumber()).element("mobile", this.getMobile())
				.element("userNumber", this.getUserNumber()).element("orderType", this.getOrderType().getValue())
				.toString();
	}

	// 退款状态
	// /private TrafficChargeOrderRefund refund;

}
