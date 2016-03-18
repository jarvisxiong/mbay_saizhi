package org.sz.mbay.promotion.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.promotion.bean.PromotionCampaignPackage;

/**
 * @Description: 流量产品dao
 * @author frank.zong
 * @date 2015-8-26 上午11:22:42
 * 		
 */
public interface PromotionCampaignPackageDao
		extends BaseDao<PromotionCampaignPackage> {
		
	/**
	 * 根据活动编号查询流量产品
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public List<PromotionCampaignPackage> findByCampaignNumber(
			String campaignNumber);
			
	/**
	 * 根据活动编号和产品id删除对应产品
	 * 
	 * @param campaignNumber
	 * @param productId
	 * @return
	 */
	public int deleteCampaignTrafficProduct(String campaignNumber,
			long productId);
			
	/**
	 * 查询最小的流量包
	 * 
	 * @param campaignNumber
	 * @param type
	 * @return
	 */
	public PromotionCampaignPackage findValidMin(String campaignNumber,
			OperatorType type);
			
	/**
	 * 根据活动编号和运营商查找流量包
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public List<PromotionCampaignPackage> findByCampaignNumberAndOperatorType(
			String campaignNumber, OperatorType type);
}
