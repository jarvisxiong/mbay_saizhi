package org.sz.mbay.promotion.service;

import java.util.List;

import org.sz.mbay.promotion.bean.PromotionCampaignMbay;

/**
 * @Description: MB产品Service层接口
 * @author frank.zong
 * @date 2015-08-26 下午14:16:26
 * 		
 */
public interface PromotionCampaignMbayService {
	
	/**
	 * 根据活动编号查询MB产品
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public List<PromotionCampaignMbay> findByCampaignNumber(
			String campaignNumber);
}
