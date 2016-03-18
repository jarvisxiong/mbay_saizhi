package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftConfig;

public interface TrafficRedMbayGiftConfigDao extends
		BaseDao<TrafficRedMbayGiftConfig> {

	int insert(TrafficRedMbayGiftConfig mbayGiftConfig);

	TrafficRedMbayGiftConfig selectById(Long id);

	int updateByIdSelective(TrafficRedMbayGiftConfig mbayGiftConfig);
	
}
