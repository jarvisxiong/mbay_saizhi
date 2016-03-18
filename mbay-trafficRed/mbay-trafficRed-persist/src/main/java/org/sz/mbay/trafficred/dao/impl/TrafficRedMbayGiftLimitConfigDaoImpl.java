package org.sz.mbay.trafficred.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.redis.common.aop.RedisCache;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftLimitConfig;
import org.sz.mbay.trafficred.dao.TrafficRedMbayGiftLimitConfigDao;

@Repository
public class TrafficRedMbayGiftLimitConfigDaoImpl
		extends BaseDaoImpl<TrafficRedMbayGiftLimitConfig>
		implements TrafficRedMbayGiftLimitConfigDao {
		
	@RedisCache
	@Override
	public TrafficRedMbayGiftLimitConfig select() {
		return template.selectOne("TrafficRedMbayGiftLimitConfig.select");
	}
	
	@RedisUpdate("select")
	@Override
	public int updateById(TrafficRedMbayGiftLimitConfig config) {
		return template.update("TrafficRedMbayGiftLimitConfig.updateById",
				config);
	}
	
	@RedisUpdate("select")
	@Override
	public int updateByIdSelective(TrafficRedMbayGiftLimitConfig config) {
		return template.update(
				"TrafficRedMbayGiftLimitConfig.updateByIdSelective",
				config);
	}
	
}
