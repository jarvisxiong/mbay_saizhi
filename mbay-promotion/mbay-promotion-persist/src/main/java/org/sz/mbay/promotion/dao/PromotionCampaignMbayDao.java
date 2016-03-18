package org.sz.mbay.promotion.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.promotion.bean.PromotionCampaignMbay;

/**
 * @Description: MB产品dao
 * @author frank.zong
 * @date 2015-8-26 上午11:22:42
 * 		
 */
public interface PromotionCampaignMbayDao
		extends BaseDao<PromotionCampaignMbay> {
		
	/**
	 * 根据活动编号查询MB产品
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public List<PromotionCampaignMbay> findByCampaignNumber(
			String campaignNumber);
			
	/**
	 * 根据活动编号和产品id删除对应产品
	 * 
	 * @param campaignNumber
	 * @param productId
	 * @return
	 */
	public int deleteCampaignMbayProduct(String campaignNumber, long productId);
	
	/**
	 * 找到最小流量包
	 * 权重大于0
	 * 
	 * @param id
	 * @return
	 */
	public PromotionCampaignMbay findValidMin(String campaignNumber);
}
