package org.sz.mbay.trafficred.service;

import java.util.List;

import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignMbay;

/**
 * 美贝流量服务
 * 
 * @author Fenlon
 * 
 */
public interface TrafficRedCampaignMbayService {
	
	/**
	 * 查找
	 * 
	 * @param id
	 * @return
	 */
	TrafficRedCampaignMbay find(Long id);
	
	/**
	 * 创建
	 * 
	 * @param mbay
	 * @return
	 */
	TrafficRedCampaignMbay create(TrafficRedCampaignMbay mbay);
	
	/**
	 * 根据活动ID查找mbay包
	 * 
	 * @param cid
	 * @return
	 */
	List<TrafficRedCampaignMbay> findByCampaignId(Long cid);
	
	/**
	 * 选择美贝
	 * 
	 * @param trcps
	 * @return
	 */
	ExecuteResult chooseMbay(TrafficRedCampaignMbay[] trcps);
	
	/**
	 * 找到最小流量包
	 * 权重大于0
	 * 
	 * @param id
	 * @return
	 */
	TrafficRedCampaignMbay findValidMin(Long id);
	
	/**
	 * 获取某活动美贝产品数量
	 * 
	 * @param id
	 * @return
	 */
	int countByCampaignId(Long id);
}
