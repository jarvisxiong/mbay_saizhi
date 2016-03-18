package org.sz.mbay.trafficred.service;

import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftConfig;

/**
 * MB送人服务层
 * 
 * @author jerry
 */
public interface TrafficRedMbayGiftConfigService extends BaseService {
	
	/**
	 * 创建实体
	 * 
	 * @param mbayGiftConfig
	 * @return
	 */
	boolean create(TrafficRedMbayGiftConfig mbayGiftConfig);
	
	/**
	 * 查找实体
	 * 
	 * @param id
	 * @return
	 */
	TrafficRedMbayGiftConfig findById(Long id);
	
	/**
	 * 更新
	 * 
	 * @param mbayGiftConfig
	 * @return
	 */
	boolean updateByIdSelective(TrafficRedMbayGiftConfig mbayGiftConfig);
	
}
