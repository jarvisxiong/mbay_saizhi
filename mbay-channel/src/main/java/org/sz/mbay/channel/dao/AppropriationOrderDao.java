package org.sz.mbay.channel.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.AppropriationOrder;

/**
 *@author Tom
 *@version 创建时间：2014-10-15上午10:28:44
 *@type 类型描述  
 */
public interface AppropriationOrderDao extends BaseDao<AppropriationOrder> {

	/**
	 * 根据订单号查找订单
	 * 
	 * @param orderNumber
	 * @return
	 */
	public AppropriationOrder findAppropriationOrder(String orderNumber);
}
