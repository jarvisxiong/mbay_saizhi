package org.sz.mbay.trafficred.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TimeQuantum;

/**
 * 时间段
 * 
 * @author Fenlon
 */
public interface TimeQuantumDao extends BaseDao<TimeQuantum> {
	
	int create(TimeQuantum timeQuantum);
	
	int createSelective(TimeQuantum timeQuantum);
	
	List<TimeQuantum> findByCampaignId(Long campaignId);
	
	void deleteAllByCampaignId(long campaignId);
}
