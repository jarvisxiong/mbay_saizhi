package org.sz.mbay.trafficred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.trafficred.bean.TrafficRedRaffle;
import org.sz.mbay.trafficred.dao.TrafficRedRaffleDao;
import org.sz.mbay.trafficred.service.TrafficRedRaffleService;

@Service
public class TrafficRedRaffleServiceImpl extends BaseServiceImpl
		implements TrafficRedRaffleService {
		
	@Autowired
	private TrafficRedRaffleDao raffleDao;
	
	@Override
	public boolean create(TrafficRedRaffle bean) {
		return raffleDao.insert(bean) == 1;
	}
	
	@Override
	public boolean updateByIdSelective(TrafficRedRaffle bean) {
		return raffleDao.updateByIdSelective(bean) == 1;
	}
	
	@Override
	public TrafficRedRaffle findById(Long id) {
		return raffleDao.selectById(id);
	}

	@Override
	public TrafficRedRaffle findByMobile(String mobile) {
		return raffleDao.selectByMobile(mobile);
	}
	
}
