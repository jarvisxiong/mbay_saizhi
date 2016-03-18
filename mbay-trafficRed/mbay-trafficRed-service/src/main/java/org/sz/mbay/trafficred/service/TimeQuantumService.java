package org.sz.mbay.trafficred.service;

import java.util.List;

import org.sz.mbay.trafficred.bean.TimeQuantum;

/**
 * 时间段服务类
 * 
 * @author Fenlon
 * 		
 */
public interface TimeQuantumService {
	
	/**
	 * 创建
	 * 
	 * @param timeQuantum
	 * @return
	 */
	public TimeQuantum create(TimeQuantum timeQuantum);
	
	/**
	 * 根据活动ID查找
	 * 
	 * @return
	 */
	public List<TimeQuantum> findByCampaignId(Long campaignId);
	
	/**
	 * 删除所有时间段
	 * 
	 * @param campaignId
	 * @return
	 */
	public void deleteAllByCampaignId(long campaignId);
}
