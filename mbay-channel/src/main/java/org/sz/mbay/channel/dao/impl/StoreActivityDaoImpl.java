package org.sz.mbay.channel.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.channel.dao.StoreActivityDao;
import org.sz.mbay.promotion.bean.CampaignStatistics;

@Repository
public class StoreActivityDaoImpl extends BaseDaoImpl<StoreCampaign>
		implements StoreActivityDao {
	
	@Override
	public List<StoreCampaign> findActivitiesByUser(String userNumber) {
		return this.template.selectList("findCampaignsByUser", userNumber);
	}
	
	@Override
	public double findDeliverPriceById(long campaignId) {
		return this.template.selectOne("findDeliverPriceById", campaignId);
	}
	
	@Override
	public double findRedeemPriceById(String campaignId) {
		return this.template.selectOne("findRedeemPriceById", campaignId);
	}
	
	@Override
	public int checkIsExpire(String userNumber) {
		
		this.template.update("checkO2OCampaignIsExpire", userNumber);
		return 0;
	}
	
	@Override
	public int updateStatus(long id, String userNumber, CampaignStatus status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userNumber", userNumber);
		map.put("status", status);
		return this.template.update("updateStormCampaignStatus", map);
	}
	
	@Override
	public int updateRedeemNumAndLockMbayAndCostMbay(long campaignId, String userNumber, double price) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("userNumber", userNumber);
		map.put("price", price);
		return this.template.update("updateRedeemNumAndLockMbayAndCostMbay", map);
	}
	
	@Override
	public int updateDeliverNumAndLockMbayAndCostMbay(long campaignId, String userNumber, double price) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("userNumber", userNumber);
		map.put("price", price);
		int s = this.template.update("updateDeliverNumAndLockMbayAndCostMbay", map);
		return s;
	}
	
	@Override
	public int updateLockMbay(long id, double price) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", id);
		map.put("price", price);
		int s = this.template.update("updateLockMbay", map);
		return s;
	}
	
	@Override
	public CampaignStatistics statisticCampaign(String usernumber) {
		return this.template.selectOne("statisticStoreCampaign", usernumber);
	}
}
