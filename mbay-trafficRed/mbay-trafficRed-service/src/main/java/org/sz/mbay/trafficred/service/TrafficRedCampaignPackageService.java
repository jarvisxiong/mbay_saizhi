package org.sz.mbay.trafficred.service;

import java.util.List;

import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficred.bean.ClassifyPackageNums;
import org.sz.mbay.trafficred.bean.TrafficRedCampaignPackage;

/**
 * 美贝产品（流量包）
 * 
 * @author Fenlon
 * 		
 */
public interface TrafficRedCampaignPackageService {
	
	/**
	 * 创建流量包
	 * 
	 * @param trafficPackage
	 * @return
	 */
	public TrafficRedCampaignPackage create(
			TrafficRedCampaignPackage trafficPackage);
			
	/**
	 * 根据活动ID查找流量包
	 * 
	 * @param cid
	 * @return
	 */
	public List<TrafficRedCampaignPackage> findByCampaignId(Long cid);
	
	/**
	 * 根据活动ID和运营商查找流量包
	 * 
	 * @param cid
	 * @return
	 */
	public List<TrafficRedCampaignPackage> findByCampaignIdAndOperatorType(
			Long cid, OperatorType type);
			
	/**
	 * 查询最小的流量包
	 * 
	 * @param cid
	 * @param valueOf
	 * @return
	 */
	public TrafficRedCampaignPackage findValidMin(Long cid,
			OperatorType valueOf);
			
	/**
	 * 删除活动所有的流量包
	 * 
	 * @param cid
	 * @return
	 */
	public Boolean deleteByCampaignId(Long cid);
	
	/**
	 * 选择流量包
	 * 
	 * @param trcps
	 * @return
	 */
	public ExecuteResult chooseTraffic(TrafficRedCampaignPackage[] trcps);
	
	/**
	 * 更新
	 * 
	 * @param pack
	 * @return
	 */
	public boolean updateById(TrafficRedCampaignPackage pack);
	
	/**
	 * 查询活动设置的不同运营商流量包个数
	 * 
	 * @param id
	 * @return [移动，联通， 电信]
	 */
	public ClassifyPackageNums findClassifyPackages(Long id);
}
