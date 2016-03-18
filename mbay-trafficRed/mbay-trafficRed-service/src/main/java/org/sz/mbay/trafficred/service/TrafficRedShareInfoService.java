package org.sz.mbay.trafficred.service;

import org.sz.mbay.trafficred.bean.TrafficRedShareInfo;

/**
 * 流量红包分享信息
 * 
 * @author jerry
 */
public interface TrafficRedShareInfoService {
	
	/**
	 * 创建实体
	 * 
	 * @param shareInfo
	 */
	void create(TrafficRedShareInfo shareInfo);
	
	/**
	 * 创建实体
	 * 
	 * @param shareInfo
	 * @param selective
	 */
	void createSelective(TrafficRedShareInfo shareInfo);
	
	/**
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 */
	TrafficRedShareInfo findById(long id);
	
	/**
	 * 更新
	 * 
	 * @param shareInfo
	 */
	void updateById(TrafficRedShareInfo shareInfo);
	
	/**
	 * 更新
	 * 
	 * @param shareInfo
	 * @param selective
	 */
	void updateByIdSelective(TrafficRedShareInfo shareInfo);
	
}
