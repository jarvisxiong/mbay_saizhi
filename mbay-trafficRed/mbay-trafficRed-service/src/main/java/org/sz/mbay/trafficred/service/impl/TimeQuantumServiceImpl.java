package org.sz.mbay.trafficred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.trafficred.bean.TimeQuantum;
import org.sz.mbay.trafficred.dao.TimeQuantumDao;
import org.sz.mbay.trafficred.service.TimeQuantumService;

/**
 * 时间段
 * 
 * @author Fenlon
 * 
 */
@Service
public class TimeQuantumServiceImpl extends BaseServiceImpl implements
		TimeQuantumService {
	
	@Autowired
	TimeQuantumDao timeQuantumDao;
	
	@Override
	public TimeQuantum create(TimeQuantum timeQuantum) {
		return this.timeQuantumDao.create(timeQuantum) > 0 ? timeQuantum : null;
	}
	
	@Override
	public List<TimeQuantum> findByCampaignId(Long campaignId) {
		return this.timeQuantumDao.findByCampaignId(campaignId);
	}
	
	@Override
	public void deleteAllByCampaignId(long campaignId) {
		this.timeQuantumDao.deleteAllByCampaignId(campaignId);
	}
}
