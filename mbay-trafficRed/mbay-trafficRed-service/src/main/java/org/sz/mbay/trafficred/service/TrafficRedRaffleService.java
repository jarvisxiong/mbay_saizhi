package org.sz.mbay.trafficred.service;

import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.trafficred.bean.TrafficRedRaffle;

public interface TrafficRedRaffleService extends BaseService {
	
	/**
	 * 创建实体
	 * 
	 * @param bean
	 * @return
	 */
	boolean create(TrafficRedRaffle bean);
	
	/**
	 * 更新实体
	 * 
	 * @param bean
	 * @return
	 */
	boolean updateByIdSelective(TrafficRedRaffle bean);
	
	/**
	 * 根据id查找实体
	 * 
	 * @param id
	 * @return
	 */
	TrafficRedRaffle findById(Long id);
	
	/**
	 * 根据手机号查找记录
	 * 
	 * @param mobile
	 * @return
	 */
	TrafficRedRaffle findByMobile(String mobile);
}
