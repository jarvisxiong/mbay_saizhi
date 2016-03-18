package org.sz.mbay.channel.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.channel.bean.StoreCampaignRecord;

public interface StoreCampaignRecordDao extends BaseDao<StoreCampaignRecord> {
	
	int updateDeliverNum(long storeId, long campaignId);
	
	int updateRedeemNum(long storeId, long campaignId);
	
}
