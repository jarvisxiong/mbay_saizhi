package org.sz.mbay.trafficred.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.trafficred.bean.TrafficRedAdvancedConfig;
import org.sz.mbay.trafficred.dao.TrafficRedAdvancedDao;
import org.sz.mbay.trafficred.service.TrafficRedAdvancedService;

@Service
public class TrafficRedAdvancedServiceImpl implements TrafficRedAdvancedService {
	
	@Autowired
	TrafficRedAdvancedDao advancedDao;

	@Override
	public void createAdvanceConfig(long campaignId) {
		TrafficRedAdvancedConfig config=new TrafficRedAdvancedConfig();
		config.setCampaignId(campaignId);
		config.setEnabled(false);
		config.setKey(generateKey());
		advancedDao.createBean(config);
	}

	@Override
	public TrafficRedAdvancedConfig findAdvancedConfig(long campaginId) {
		return this.advancedDao.getBean(campaginId);
	}

	@Override
	public String resetKey(long campaginId) {
		String key=generateKey();
		this.advancedDao.updateAdvancedConfigKey(campaginId, key);
		return key;
	}
	
	private static String generateKey(){
		return UUID.randomUUID().toString();
	}

	
	@Override
	public void disableTrafficRedAdvanced(long campaignId) {
		this.advancedDao.updateAdvancedConfigEnableStatus(campaignId, false);
		
	}

	@Override
	public void enabledTrafficRedAdvanced(long campaignId) {
		this.advancedDao.updateAdvancedConfigEnableStatus(campaignId, true);
		
	}
	
	
}
