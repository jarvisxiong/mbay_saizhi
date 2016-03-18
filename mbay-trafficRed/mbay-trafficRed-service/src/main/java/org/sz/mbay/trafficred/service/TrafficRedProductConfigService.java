package org.sz.mbay.trafficred.service;

import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.enums.ProductType;

public interface TrafficRedProductConfigService extends BaseService {
	
	/**
	 * 新增实体
	 * 
	 * @param config
	 * @return
	 */
	boolean create(TrafficRedProductConfig config);
	
	/**
	 * 重置单日上限
	 */
	public void resetDailyRemain();
	
	/**
	 * 查询活动是否选择了指定产品
	 * 
	 * @param campaignId
	 *            活动ID
	 * @param productType
	 *            产品类型
	 * @return
	 */
	public boolean isSelectedProduct(long campaignId, ProductType productType);
	
	/**
	 * 增加活动
	 * 
	 * @param campaignId
	 * @param increasePool
	 * @return
	 */
	public ExecuteResult increaseProductPoolSize(long campaignId,
			ProductType productType, double increasePool,
			String userNumber);
			
	/**
	 * 产品剩余是否小于阀值
	 * 
	 * @param campaignId
	 * @param productType
	 * @return
	 */
	public boolean isProductPoolRemainLessThanThreshold(long campaignId,
			ProductType productType);
			
	/**
	 * 修改产品警告状态为已警告
	 * 
	 * @param campaignId
	 * @param productType
	 */
	public boolean changeProductThresholdWarned(long campaignId,
			ProductType productType);
			
	/**
	 * 修改产品当日上线
	 * 
	 * @param campaignId
	 * @param productType
	 * @param dailyLimit
	 * @return
	 */
	public ExecuteResult updateProductThreshold(long campaignId,
			ProductType productType, int threshold);
			
	/**
	 * 美贝剩余流量池减少
	 * 
	 * @param id
	 * @param mbay
	 */
	public boolean reduceProductPoolRemain(long campaignId,
			ProductType productType, double mbay);
			
	/**
	 * 美贝池单日剩余流量减少
	 * 
	 * @param id
	 * @param mbay
	 * @return 扣减是否成功
	 */
	public boolean reduceProductDailyRemain(long campaignId,
			ProductType productType, double mbay);
			
	/**
	 * 查询产品当日上线
	 * 
	 * @param campaignId
	 *            活动ID
	 * @param productType
	 *            产品类型
	 * @return
	 */
	public double findProductDailyLimit(long campaignId,
			ProductType productType);
			
	/**
	 * 修改产品当日上线
	 * 
	 * @param campaignId
	 * @param productType
	 * @param dailyLimit
	 * @return
	 */
	public ExecuteResult updateProductDailyLimit(long campaignId,
			ProductType productType, double dailyLimit);
			
	/**
	 * 查询活动产品配置信息
	 * 
	 * @param campaignId
	 *            活动编号
	 * @param productType
	 *            产品类型
	 * @return 产品配置信息
	 */
	public TrafficRedProductConfig findProductConfig(long campaignId,
			ProductType productType);
			
	/**
	 * 查询活动警告信息
	 * 
	 * @param campaignId
	 * @return
	 */
	public ThresholdWarnInfo findThresholdWarnInfo(long campaignId,
			ProductType productType);
			
	/**
	 * 活动结束后清除数据
	 * 
	 * @param id
	 */
	void clearConfig(Long id);
}
