package org.sz.mbay.promotion.service;

import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.promotion.bean.PromotionProductConfig;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.enums.ProductType;

/**
 * @Description: 产品配置Service层接口
 * @author frank.zong
 * @date 2015-08-26 下午14:16:26
 * 		
 */
public interface PromotionProductConfigService {
	
	/**
	 * 根据活动编号和产品类型查询产品配置信息
	 * 
	 * @param campaignNumber
	 * @param type
	 * @return
	 */
	public PromotionProductConfig findProductConfig(String campaignNumber,
			ProductType type);
			
	/**
	 * 修改单日上限
	 * 
	 * @param campaignNumber
	 * @param productType
	 * @param dailyLimit
	 * @return
	 */
	public ExecuteResult updateProductDailyLimit(String campaignNumber,
			ProductType productType, double dailyLimit);
			
	/**
	 * 修改小池
	 * 
	 * @param campaignNumber
	 * @param productType
	 * @param increasePool
	 * @param userNumber
	 * @return
	 */
	public ExecuteResult increaseProductPoolSize(String campaignNumber,
			ProductType productType, double increasePool,
			String userNumber);
			
	/**
	 * 查询活动是否选择了指定产品
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param productType
	 *            产品类型
	 * @return
	 */
	public boolean isSelectedProduct(String campaignNumber,
			ProductType productType);
			
	/**
	 * 修改告警阀值
	 * 
	 * @param campaignNumber
	 * @param productType
	 * @param dailyLimit
	 * @return
	 */
	public ExecuteResult updateProductThreshold(String campaignNumber,
			ProductType productType, int threshold);
			
	/**
	 * 美贝剩余流量池减少
	 * 
	 * @param campaignNumber
	 * @param mbay
	 */
	public boolean reduceProductPoolRemain(String campaignNumber,
			ProductType productType, double mbay);
			
	/**
	 * 美贝池单日剩余流量减少
	 * 
	 * @param campaignNumber
	 * @param mbay
	 * @return 扣减是否成功
	 */
	public boolean reduceProductDailyRemain(String campaignNumber,
			ProductType productType, double mbay);
			
	/**
	 * 查询产品当日上线
	 * 
	 * @param campaignNumber
	 *            活动编号
	 * @param productType
	 *            产品类型
	 * @return
	 */
	public double findProductDailyLimit(String campaignNumber,
			ProductType productType);
			
	/**
	 * 查询活动警告信息
	 * 
	 * @param campaignNumber
	 * @param productType
	 * @return
	 */
	public ThresholdWarnInfo findThresholdWarnInfo(String campaignNumber,
			ProductType productType);
			
	/**
	 * 修改产品警告状态为已警告
	 * 
	 * @param campaignNumber
	 * @param productType
	 */
	public boolean changeProductThresholdWarned(String campaignNumber,
			ProductType productType);
			
	/**
	 * 产品剩余是否小于阀值
	 * 
	 * @param campaignNumber
	 * @param productType
	 * @return
	 */
	public boolean isProductPoolRemainLessThanThreshold(String campaignNumber,
			ProductType productType);
			
	/**
	 * 重置单日上限
	 */
	public void resetDailyRemain();
}
