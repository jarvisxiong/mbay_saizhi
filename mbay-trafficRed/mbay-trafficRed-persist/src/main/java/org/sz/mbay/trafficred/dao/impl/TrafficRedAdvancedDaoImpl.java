package org.sz.mbay.trafficred.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.trafficred.bean.TrafficRedAdvancedConfig;
import org.sz.mbay.trafficred.dao.TrafficRedAdvancedDao;

@Repository
public class TrafficRedAdvancedDaoImpl extends
		BaseDaoImpl<TrafficRedAdvancedConfig>implements TrafficRedAdvancedDao {
		
	@Override
	public void updateAdvancedConfigKey(long campaignId, String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("key", key);
		this.template.update("updateAdvanceedConfigKey", map);
		
	}
	
	@Override
	public void updateAdvancedConfigEnableStatus(long campaignId,
			boolean status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("enabled", status);
		this.template.update("updateConfigEnableStatus", map);
	}
	
}
