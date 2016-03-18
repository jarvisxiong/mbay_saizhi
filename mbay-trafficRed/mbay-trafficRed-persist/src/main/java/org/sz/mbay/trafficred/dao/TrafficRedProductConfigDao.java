package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.enums.ProductType;

public interface TrafficRedProductConfigDao
		extends BaseDao<TrafficRedProductConfig> {
		
	int create(TrafficRedProductConfig config);
	
	void resetDailyRemain();
	
	boolean isSelectedProduct(long campaignId, ProductType productType);
	
	int increaseProductPoolSize(long campaignId, ProductType productType,
			double increasePool);
			
	public boolean isProductPoolRemainLessThanThreshold(long campaignId,
			ProductType productType);
			
	public void resetProductThsholdWarnStatus(long campaignId,
			ProductType productType);
			
	boolean changeProductThresholdWarned(long campaignId,
			ProductType productType);
			
	boolean updateProductThreshold(long campaignId, ProductType productType,
			int threshold);
			
	int reduceProductPoolRemain(long campaignId, ProductType productType,
			double mbay);
			
	double findProductDailyLimit(long campaignId, ProductType productType);
	
	int reduceProductDailyRemain(long campaignId, ProductType productType,
			double mbay);
			
	boolean updateProductDailyLimit(long campaignId, ProductType productType,
			double dailyLimit);
			
	TrafficRedProductConfig findTrafficRedProductConfig(long campaignId,
			ProductType productType);
			
	ThresholdWarnInfo findThresholdWarnInfo(long campaignId,
			ProductType productType);
			
	int clearConfig(Long id);
	
}
