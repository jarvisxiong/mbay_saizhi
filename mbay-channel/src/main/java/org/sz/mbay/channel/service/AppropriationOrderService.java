package org.sz.mbay.channel.service;

import org.sz.mbay.channel.bean.AppropriationOrder;

/**
 * 拨款
 * 
 * @author jerry
 */
public interface AppropriationOrderService {
	
	/**
	 * 根据订单号查找订单
	 * 
	 * @param orderNumber
	 * @return
	 */
	AppropriationOrder findAppropriationOrder(String orderNumber);
}
