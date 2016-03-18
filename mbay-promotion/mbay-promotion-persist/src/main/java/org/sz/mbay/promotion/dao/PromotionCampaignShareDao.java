package org.sz.mbay.promotion.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.promotion.bean.PromotionCampaignShare;

/**
 * @Description: 分享信息dao
 * @author frank.zong
 * @date 2015-9-22 上午11:22:42
 * 		
 */
public interface PromotionCampaignShareDao
		extends BaseDao<PromotionCampaignShare> {
		
	/**
	 * 根据活动编号查询分享信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public PromotionCampaignShare findByCampaignNumber(String campaignNumber);
	
	/**
	 * 更新分享信息
	 * 
	 * @param shareInfo
	 * @return 
	 */
	public void updateShare(PromotionCampaignShare shareInfo);
	
}
