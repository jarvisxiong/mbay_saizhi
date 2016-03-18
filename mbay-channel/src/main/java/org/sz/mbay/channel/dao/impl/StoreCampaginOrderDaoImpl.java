package org.sz.mbay.channel.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.StoreCampaignOrder;
import org.sz.mbay.channel.dao.StoreCampaginOrderDao;

@Repository
public class StoreCampaginOrderDaoImpl extends BaseDaoImpl<StoreCampaignOrder> implements StoreCampaginOrderDao {
	
	@Override
	public StoreCampaignOrder findOrderByNumber(String ordernumber) {
		return this.template.selectOne("findOrderByNumber", ordernumber);
	}
}
