package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedTemplate;

/**
 * 流量红包活动dao
 * 
 * @author Fenlon
 * 		
 */
public interface TrafficRedTemplateDao extends BaseDao<TrafficRedTemplate> {
	
	int create(TrafficRedTemplate template);
	
	TrafficRedTemplate selectById(Long id);
	
	int updateByIdSelective(TrafficRedTemplate template);
	
	int clearShakeCustomize(Long id);
	
	int createShakeCustomize(TrafficRedTemplate bean);
	
	boolean isExistShakeCustomize(Long cid);
}
