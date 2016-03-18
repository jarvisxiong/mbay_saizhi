package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftLimitConfig;

public interface TrafficRedMbayGiftLimitConfigDao
		extends BaseDao<TrafficRedMbayGiftLimitConfig> {

	TrafficRedMbayGiftLimitConfig select();

	int updateById(TrafficRedMbayGiftLimitConfig config);

	int updateByIdSelective(TrafficRedMbayGiftLimitConfig config);
		
}
