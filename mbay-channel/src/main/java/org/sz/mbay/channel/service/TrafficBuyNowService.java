package org.sz.mbay.channel.service;

public interface TrafficBuyNowService {
	
	/**
	 * 流量直充
	 *  
	 * @param orderNumber 订单号
	 */
	public void rechargeTrffic(String orderNumber,String userNumber);

}