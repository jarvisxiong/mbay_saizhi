package org.sz.mbay.trafficred.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.trafficred.bean.CampaignStatistics;
import org.sz.mbay.trafficred.bean.TrafficRedCampaign;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignMbay;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignPackage;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.enums.ProductType;

/**
 * 流量红包活动服务
 * 
 * @author Fenlon
 * 		
 */
public interface TrafficRedCampaignService {
	
	/**
	 * 创建流量红包活动
	 * 
	 * @param campaign
	 * @return
	 */
	
	public void create(TrafficRedCampaign campaign,
			List<TrafficRedProductConfig> productConfigs);
			
	/**
	 * 根据用户名分页活动
	 * 
	 * @param pageInfo
	 * @param userNumber
	 * @return
	 */
	public List<TrafficRedCampaign> findAllByPage(PageInfo pageInfo,
			String userNumber);
			
	/**
	 * 取消活动
	 * 
	 * @param id
	 * @param userNumber
	 * @return
	 */
	public ExecuteResult cancel(long id);
	
	/**
	 * 此方法主要归咎于前期使用活动标识不统一，有的地方用Id/有的地方用编号。 根据活动Id查询活动编号
	 * 
	 * 
	 * @param comapignId
	 * @return
	 */
	public String findCampaignNumberById(long campaignId);
	
	/**
	 * 查找活动信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public TrafficRedCampaign findCampaignByNumber(String campaignNumber);
	
	/**
	 * 跟新活动
	 * 
	 * @param campaign
	 * @return
	 */
	public TrafficRedCampaign updateByIdSelective(TrafficRedCampaign campaign);
	
	/**
	 * 查找活动
	 * 
	 * @param id
	 * @return
	 */
	public TrafficRedCampaign find(Long id);
	
	/**
	 * 删除对应的活动产品
	 * 
	 * @param campaignId
	 *            活动编号
	 * @param ptype
	 *            产品类型
	 * @param productId
	 * @return
	 */
	public boolean deleteCampaignProduct(long campaignId, ProductType ptype,
			long productId);
			
	/**
	 * @param campaignId
	 *            活动编号
	 * @param ptype
	 *            产品类型
	 * @param productId
	 *            产品编号
	 * @param ratio
	 *            权重
	 * @return
	 */
	public boolean updateProductRatio(long campaignId, ProductType ptype,
			long productId, int ratio);
			
	/**
	 * 判断是否存在该活动
	 * 
	 * @param userNumber
	 * @param campaignNumber
	 * @return
	 */
	public boolean isExistCampaign(long campaignId);
	
	/**
	 * 为活动添加流量包产品
	 * 
	 * @param redpackages
	 */
	public void addTrafficProducts(long campaignId,
			List<TrafficRedCampaignPackage> redpackages,
			TrafficRedProductConfig config);
			
	/**
	 * 添加活动产品
	 * 
	 * @param mbayProducts
	 *            活动
	 * @param config
	 *            非第一次添加，请传null
	 */
	public void addMbayProducts(long campaignId,
			List<TrafficRedCampaignMbay> mbayProducts,
			TrafficRedProductConfig config);
	
	/**
	 * 修改活动信息
	 * 
	 * @param campaign
	 */
	public void updateCampaignInfo(TrafficRedCampaign campaign);
	
	/**
	 * 根据不同状态统计数据
	 * 
	 * @param usernumber
	 * @return
	 */
	public CampaignStatistics statisticCampaign(String userNumber);
	
	/**
	 * 根据活动ID查询活动归属用户
	 * 
	 * @param campignId
	 * @return 用户编号
	 */
	public String findCampaignBelongUser(long campaignId);
	
	/**
	 * 获取产品中奖概率
	 * 
	 * @param campaignId
	 *            活动编号
	 * @return 中奖概率
	 */
	public double findProductHitRate(long campaignId);
	
	/**
	 * 检查活动产品池余额是否低于阀值并发送警告
	 * 
	 * @param campaignId
	 * @param productType
	 */
	public void checkThresholdAndSendWarning(long campaignId,
			ProductType productType);
			
	/**
	 * 根据编号获取id
	 * 
	 * @param number
	 * @return
	 */
	public Long getIdByNumber(String number);
	
}
