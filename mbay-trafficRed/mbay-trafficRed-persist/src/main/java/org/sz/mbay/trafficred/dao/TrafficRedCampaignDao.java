package org.sz.mbay.trafficred.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.CampaignStatistics;
import org.sz.mbay.trafficred.bean.TrafficRedCampaign;

/**
 * 流量红包活动
 * 
 * @author Fenlon
 * 		
 */
public interface TrafficRedCampaignDao extends BaseDao<TrafficRedCampaign> {
	
	int createSelective(TrafficRedCampaign campaign);
	
	TrafficRedCampaign selectById(Long id);
	
	int updateByIdSelective(TrafficRedCampaign campaign);
	
	int updateById(TrafficRedCampaign campaign);
	
	TrafficRedCampaign findCampaignByNumber(String campaignNumber);
	
	boolean isExistCampaign(long campaignId);
	
	CampaignStatistics statisticCampaign(String userNumber);
	
	String findCampaignNumberById(long campaignId);
	
	String findCampaignBelongUser(long campaignId);
	
	double findProductHitRate(long campaignId);
	
	List<String> findAllCampaignNumberStartToday();
	
	List<String> findAllCampaignNumberOverToday();
	
	Long getIdByNumber(String number);
	
}
