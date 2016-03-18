package org.sz.mbay.channel.service;

import org.sz.mbay.channel.bean.StoreCampaignOrder;

public interface StoreCampaginOrderService {
	
	StoreCampaignOrder create(StoreCampaignOrder order);
	
	StoreCampaignOrder findStoreCampaignOrder(String ordernumber);
	
}
