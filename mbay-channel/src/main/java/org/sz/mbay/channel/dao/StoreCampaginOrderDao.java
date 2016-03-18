package org.sz.mbay.channel.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.StoreCampaignOrder;

public interface StoreCampaginOrderDao extends BaseDao<StoreCampaignOrder> {
	
	StoreCampaignOrder findOrderByNumber(String ordernumber);
	
}
