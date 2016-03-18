package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedAdvancedConfig;

public interface TrafficRedAdvancedDao extends BaseDao<TrafficRedAdvancedConfig> {
	
	public void updateAdvancedConfigKey(long campaignId,String key);
	
	public void updateAdvancedConfigEnableStatus(long campaignId,boolean status);

}
