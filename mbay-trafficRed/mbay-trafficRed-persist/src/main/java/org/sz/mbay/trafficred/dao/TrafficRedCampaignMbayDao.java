package org.sz.mbay.trafficred.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignMbay;

/**
 * 美贝流量dao
 * 
 * @author Fenlon
 * 		
 */
public interface TrafficRedCampaignMbayDao
		extends BaseDao<TrafficRedCampaignMbay> {
		
	int create(TrafficRedCampaignMbay record);
	
	int createSelective(TrafficRedCampaignMbay record);
	
	TrafficRedCampaignMbay findById(Long id);
	
	int updateByIdSelective(TrafficRedCampaignMbay record);
	
	int updateById(TrafficRedCampaignMbay record);
	
	List<TrafficRedCampaignMbay> findByCampaignId(Long cid);
	
	int deleteByCampaignId(Long cid);
	
	TrafficRedCampaignMbay findValidMin(Long id);
	
	int countByCampaignId(Long id);
	
	int deleteCampaignMbayProduct(long campaignId, long productId);
	
	int updateMbayProductRatio(long campaignId, long productId, int ratio);
}
