package org.sz.mbay.trafficorder.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.trafficorder.bean.TrafficOrderRecord;
import org.sz.mbay.trafficorder.dao.TrafficOrderRecordDao;
import org.sz.mbay.trafficorder.service.TrafficOrderRecordService;

@Service
public class TrafficOrderRecordServiceImpl extends BaseServiceImpl
		implements TrafficOrderRecordService {
	
	@Autowired
	private TrafficOrderRecordDao trafficOrderRecordDao;
		
	@Override
	public List<TrafficOrderRecord> findListByOrderNumber(String orderNumber) {
		return trafficOrderRecordDao.findListByOrderNumber(orderNumber);
	}

	@Override
	public boolean create(TrafficOrderRecord orderRecord) {
		orderRecord.setUserId(0);
		return trafficOrderRecordDao.insert(orderRecord) == 1;
	}
}
