package org.sz.mbay.channel.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.channel.bean.StoreCampaign;
import org.sz.mbay.promotion.bean.CampaignStatistics;

public interface StoreActivityDao extends BaseDao<StoreCampaign> {
	
	List<StoreCampaign> findActivitiesByUser(String storeId);
	
	double findDeliverPriceById(long campaignId);
	
	double findRedeemPriceById(String campaignId);
	
	int checkIsExpire(String userNumber);
	
	int updateStatus(long id, String userNumber, CampaignStatus status);
	
	int updateRedeemNumAndLockMbayAndCostMbay(long campaignId, String userNumber, double price);
	
	int updateDeliverNumAndLockMbayAndCostMbay(long campaignId, String userNumber, double price);
	
	int updateLockMbay(long id, double price);
	
	CampaignStatistics statisticCampaign(String usernumber);
}
