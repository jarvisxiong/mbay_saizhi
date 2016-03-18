package org.sz.mbay.trafficred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.trafficred.bean.TrafficRedShareInfo;
import org.sz.mbay.trafficred.dao.TrafficRedShareInfoDao;
import org.sz.mbay.trafficred.service.TrafficRedShareInfoService;

/**
 * 分享信息
 * 
 * @author jerry
 */
@Service
public class TrafficRedShareInfoServiceImpl extends BaseServiceImpl implements
		TrafficRedShareInfoService {
		
	@Autowired
	private TrafficRedShareInfoDao trafficRedShareInfoDao;
	
	@Override
	public void create(TrafficRedShareInfo shareInfo) {
		trafficRedShareInfoDao.insert(shareInfo);
	}
	
	@Override
	public void createSelective(TrafficRedShareInfo shareInfo) {
		trafficRedShareInfoDao.insertSelective(shareInfo);
	}
	
	@Override
	public TrafficRedShareInfo findById(long id) {
		return trafficRedShareInfoDao.selectById(id);
	}
	
	@Override
	public void updateById(TrafficRedShareInfo shareInfo) {
		trafficRedShareInfoDao.updateById(shareInfo);
	}
	
	@Override
	public void updateByIdSelective(TrafficRedShareInfo shareInfo) {
		trafficRedShareInfoDao.updateByIdSelective(shareInfo);
	}
	
}
