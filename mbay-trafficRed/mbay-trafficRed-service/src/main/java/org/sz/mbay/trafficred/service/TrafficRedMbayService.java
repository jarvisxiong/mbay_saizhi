package org.sz.mbay.trafficred.service;

import java.util.List;

import org.sz.mbay.trafficred.bean.TrafficRedMbay;

/**
 * 兑换美贝服务
 * 
 * @author jerry
 */
public interface TrafficRedMbayService {
	
	/**
	 * 查询所有信息
	 * 
	 * @return
	 */
	List<TrafficRedMbay> findAll();
	
	/**
	 * 删除记录
	 * 
	 * @param mid
	 */
	void deleteById(Long mid);
	
	/**
	 * 新建实体
	 * 
	 * @param bean
	 */
	TrafficRedMbay create(TrafficRedMbay bean);
	
	/**
	 * 跟新实体
	 */
	void updateById(TrafficRedMbay bean);
	
}
