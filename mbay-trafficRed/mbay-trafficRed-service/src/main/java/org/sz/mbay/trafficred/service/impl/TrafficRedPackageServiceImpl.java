package org.sz.mbay.trafficred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.trafficred.bean.TrafficRedPackage;
import org.sz.mbay.trafficred.dao.TrafficRedPackageDao;
import org.sz.mbay.trafficred.service.TrafficRedPackageService;

@Service
public class TrafficRedPackageServiceImpl extends BaseServiceImpl implements
		TrafficRedPackageService {
		
	@Autowired
	private TrafficRedPackageDao packageDao;
	
	@Override
	public TrafficRedPackage findById(Long id) {
		return this.packageDao.selectById(id);
	}
	
	@Override
	public List<TrafficRedPackage> findByOperatorType(OperatorType type) {
		return this.packageDao.findByOperatorType(type);
	}
	
	@Override
	public void deleteById(Long pid) {
		packageDao.deleteById(pid);
	}
	
	@Override
	public void addPackages(TrafficRedPackage[] trps) {
		for (TrafficRedPackage trp : trps) {
			packageDao.createSelective(trp);
		}
	}
	
	@Override
	public void deleteByOperatorType(OperatorType type) {
		packageDao.deleteByOperatorType(type);
	}
	
}
