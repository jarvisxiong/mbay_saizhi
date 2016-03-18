package org.sz.mbay.channel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.channel.bean.StoreCampaignOrder;
import org.sz.mbay.channel.dao.StoreCampaginOrderDao;
import org.sz.mbay.channel.service.StoreCampaginOrderService;

@Service
public class StoreCampaginOrderServiceImpl implements StoreCampaginOrderService {
	
	@Autowired
	StoreCampaginOrderDao orderDao;
	
	@Override
	public StoreCampaignOrder create(StoreCampaignOrder order) {
		long id = PKgen.getInstance().nextPK();
		order.setId(id);
		try {
			return this.orderDao.createBean(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public StoreCampaignOrder findStoreCampaignOrder(String ordernumber) {
		return this.orderDao.findOrderByNumber(ordernumber);
	}
}
