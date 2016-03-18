package org.sz.mbay.trafficred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftLimitConfig;
import org.sz.mbay.trafficred.dao.TrafficRedMbayGiftLimitConfigDao;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftLimitService;

@Service
public class TrafficRedMbayGiftLimitServiceImpl
		implements TrafficRedMbayGiftLimitService {
	
	@Autowired
	private TrafficRedMbayGiftLimitConfigDao mbayGiftLimitConfigDao;

	@Override
	public TrafficRedMbayGiftLimitConfig find() {
		TrafficRedMbayGiftLimitConfig config = mbayGiftLimitConfigDao.select();
		if (config == null) {
			return TrafficRedMbayGiftLimitConfig.DEFAULT;
		}
		return config;
	}

	@Override
	public boolean updateById(TrafficRedMbayGiftLimitConfig config) {
		return mbayGiftLimitConfigDao.updateById(config) == 1;
	}

	@Override
	public boolean updateByIdSelective(TrafficRedMbayGiftLimitConfig config) {
		return mbayGiftLimitConfigDao.updateByIdSelective(config) == 1;
	}
	
}
