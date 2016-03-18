package org.sz.mbay.channel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.channel.bean.AppropriationOrder;
import org.sz.mbay.channel.dao.AppropriationOrderDao;
import org.sz.mbay.channel.service.AppropriationOrderService;

/**
 * @author Tom
 * @version 创建时间：2014-10-15上午10:32:32
 */

@Service
public class AppropriationOrderServiceImpl
		implements AppropriationOrderService {
		
	@Autowired
	AppropriationOrderDao appropriationDao;
	
	@Override
	public AppropriationOrder findAppropriationOrder(String orderNumber) {
		AppropriationOrder order = appropriationDao
				.findAppropriationOrder(orderNumber);
		return order == null ? new AppropriationOrder() : order;
	}
}
