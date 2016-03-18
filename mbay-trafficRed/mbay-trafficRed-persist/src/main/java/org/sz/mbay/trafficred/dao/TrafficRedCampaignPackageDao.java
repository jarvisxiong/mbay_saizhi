package org.sz.mbay.trafficred.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficred.bean.ClassifyPackageNums;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignPackage;

public interface TrafficRedCampaignPackageDao extends
		BaseDao<TrafficRedCampaignPackage> {
		
	int create(TrafficRedCampaignPackage record);
	
	int createSelective(TrafficRedCampaignPackage record);
	
	TrafficRedCampaignPackage selectById(Long id);
	
	int updateByIdSelective(TrafficRedCampaignPackage record);
	
	int updateById(TrafficRedCampaignPackage record);
	
	List<TrafficRedCampaignPackage> findByCampaignId(Long cid);
	
	TrafficRedCampaignPackage findValidMin(Long cid, OperatorType type);
	
	int deleteByCampaignId(Long cid);
	
	List<TrafficRedCampaignPackage> findByCampaignIdAndOperatorType(Long cid,
			OperatorType type);
			
	ClassifyPackageNums findClassifyPackages(Long id);
	
	int deleteCampaignTrafficProduct(long campaignId, long productId);
	
	int updateTrafficProductRatio(long campaignId, long productId, int ratio);
}
