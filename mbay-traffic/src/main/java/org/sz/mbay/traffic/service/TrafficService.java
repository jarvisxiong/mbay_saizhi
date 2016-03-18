package org.sz.mbay.traffic.service;

import org.sz.mbay.traffic.bean.TrafficChargeOrderRecord;

public interface TrafficService {
	
	
//	/**
//	 * 更具订单号查询订单
//	 * @param ordernumber
//	 * @return
//	 */
//	public TrafficChargeOrder findTrafficChargeOrder(String ordernumber);
	
//	/**
//	 * 修改订单状态和运营商流量充值状态
//	 * @param orderstatus 订单状态 为NULL则不修改
//	 * @param ops  运营商操作状态 为NULL则不修改
//	 * @return
//	 */
//	public boolean updateOrderStatus(String ordernuber,ChargeOrderStatus orderstatus,OperatorRechargeStatus ops);
	
	
	/**
	 * 创建订单操作记录
	 * @param reocord
	 */
	public void createOrderRecord(TrafficChargeOrderRecord reocord)  throws Exception;
	
	
	/**
	 * 查询某个地区昨日流量充值订单
	 * @param area 地区
	 * @param teleOperator 运营商
	 * @return
	 */
///	public List<TrafficChargeOrder> findAllYesterDayTrafficOrder(Area area,OperatorType teleOperator);
	

}
