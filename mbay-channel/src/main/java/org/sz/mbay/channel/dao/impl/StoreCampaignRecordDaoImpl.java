package org.sz.mbay.channel.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.channel.bean.StoreCampaignRecord;
import org.sz.mbay.channel.dao.StoreCampaignRecordDao;

@Repository
public class StoreCampaignRecordDaoImpl extends BaseDaoImpl<StoreCampaignRecord> implements StoreCampaignRecordDao {
	
	@Override
	public int updateDeliverNum(long storeId, long campaignId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storeId", storeId);
		map.put("campaignId", campaignId);
		return this.template.update("updateDeliverNum", map);
	}
	
	@Override
	public int updateRedeemNum(long storeId, long campaignId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storeId", storeId);
		map.put("campaignId", campaignId);
		return this.template.update("updateRedeemNum", map);
	}
	
}
