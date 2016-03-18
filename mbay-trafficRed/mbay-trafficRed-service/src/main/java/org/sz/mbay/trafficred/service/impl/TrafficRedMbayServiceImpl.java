package org.sz.mbay.trafficred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.trafficred.bean.TrafficRedMbay;
import org.sz.mbay.trafficred.dao.TrafficRedMbayDao;
import org.sz.mbay.trafficred.service.TrafficRedMbayService;

@Service
public class TrafficRedMbayServiceImpl extends BaseServiceImpl implements
		TrafficRedMbayService {
		
	@Autowired
	private TrafficRedMbayDao trafficRedMbayDao;
	
	@Override
	public List<TrafficRedMbay> findAll() {
		return trafficRedMbayDao.findAll();
	}
	
	@Override
	public void deleteById(Long mid) {
		trafficRedMbayDao.deleteById(mid);
	}
	
	@Override
	public TrafficRedMbay create(TrafficRedMbay bean) {
		trafficRedMbayDao.createSelective(bean);
		return bean;
	}
	
	@Override
	public void updateById(TrafficRedMbay bean) {
		trafficRedMbayDao.updateByIdSelective(bean);
	}
	
}
