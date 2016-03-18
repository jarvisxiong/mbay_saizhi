package org.sz.mbay.trafficred.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.redis.common.aop.RedisCache;
import org.sz.mbay.redis.common.aop.RedisUpdate;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftConfig;
import org.sz.mbay.trafficred.dao.TrafficRedMbayGiftConfigDao;

@Repository
public class TrafficRedMbayGiftConfigDaoImpl extends
		BaseDaoImpl<TrafficRedMbayGiftConfig>implements
		TrafficRedMbayGiftConfigDao {
		
	@Override
	public int insert(TrafficRedMbayGiftConfig mbayGiftConfig) {
		mbayGiftConfig.setId(PKgen.getInstance().nextPK());
		return template.insert("TrafficRedMbayGiftConfig.insert",
				mbayGiftConfig);
	}
	
	@RedisCache("0")
	@Override
	public TrafficRedMbayGiftConfig selectById(Long id) {
		return template.selectOne("TrafficRedMbayGiftConfig.selectById", id);
	}
	
	@RedisUpdate("selectById${0.id}")
	@Override
	public int updateByIdSelective(TrafficRedMbayGiftConfig mbayGiftConfig) {
		return template.update("TrafficRedMbayGiftConfig.updateByIdSelective",
				mbayGiftConfig);
	}
	
}
