package org.sz.mbay.channel.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.AppropriationOrder;
import org.sz.mbay.channel.dao.AppropriationOrderDao;

/**
 * @author Tom
 * @version 创建时间：2014-10-15上午10:29:52
 * @type 类型描述 public class AppropriationDaoImpl{ }
 */
@Repository
public class AppropriationOrderDaoImpl extends BaseDaoImpl<AppropriationOrder> implements AppropriationOrderDao {
	
	@Override
	public AppropriationOrder findAppropriationOrder(String orderNumber) {
		return template.selectOne("findAppropriationOrderByOrderNumber", orderNumber);
	}
}
