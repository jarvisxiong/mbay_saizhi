package org.sz.mbay.trafficred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftConfig;
import org.sz.mbay.trafficred.dao.TrafficRedMbayGiftConfigDao;
import org.sz.mbay.trafficred.service.TrafficRedMbayGiftConfigService;

@Service
public class TrafficRedMbayGiftConfigServiceImpl extends BaseServiceImpl
		implements TrafficRedMbayGiftConfigService {
	
	@Autowired
	private TrafficRedMbayGiftConfigDao mbayGiftConfigDao;
	
	@Override
	public boolean create(TrafficRedMbayGiftConfig mbayGiftConfig) {
		return mbayGiftConfigDao.insert(mbayGiftConfig) == 1;
	}
	
	@Override
	public TrafficRedMbayGiftConfig findById(Long id) {
		return mbayGiftConfigDao.selectById(id);
	}
	
	@Override
	public boolean updateByIdSelective(TrafficRedMbayGiftConfig mbayGiftConfig) {
		return mbayGiftConfigDao.updateByIdSelective(mbayGiftConfig) == 1;
	}
	
}
