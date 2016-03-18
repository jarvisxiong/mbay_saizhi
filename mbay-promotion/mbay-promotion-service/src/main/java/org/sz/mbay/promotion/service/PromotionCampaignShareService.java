package org.sz.mbay.promotion.service;

import org.sz.mbay.promotion.bean.PromotionCampaignShare;

/**
 * @Description: 分享Service层接口
 * @author frank.zong
 * @date 2015-09-22 下午14:16:26
 * 		
 */
public interface PromotionCampaignShareService {
	
	/**
	 * 根据活动编号查询分享信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public PromotionCampaignShare findByCampaignNumber(
			String campaignNumber);
	
	/**
	 * 添加分享信息
	 * 
	 * @param shareInfo
	 * @return 
	 */
	public void addShare(PromotionCampaignShare shareInfo);
	
	/**
	 * 更新分享信息
	 * 
	 * @param shareInfo
	 * @return 
	 */
	public void updateShare(PromotionCampaignShare shareInfo);
}
