package org.sz.mbay.promotion.service;

import java.util.List;

import org.sz.mbay.promotion.bean.PromotionCampaignPackage;

/**
 * @Description: 流量产品Service层接口
 * @author frank.zong
 * @date 2015-08-26 下午14:16:26
 * 		
 */
public interface PromotionCampaignPackageService {
	
	/**
	 * 根据活动编号查询流量产品
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public List<PromotionCampaignPackage> findByCampaignNumber(
			String campaignNumber);
}
