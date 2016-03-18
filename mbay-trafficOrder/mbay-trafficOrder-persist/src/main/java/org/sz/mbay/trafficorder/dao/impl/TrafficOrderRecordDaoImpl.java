package org.sz.mbay.trafficorder.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.trafficorder.bean.TrafficOrderRecord;
import org.sz.mbay.trafficorder.dao.TrafficOrderRecordDao;

@Repository
public class TrafficOrderRecordDaoImpl extends BaseDaoImpl<TrafficOrderRecord>
		implements TrafficOrderRecordDao {
		
	@Override
	public List<TrafficOrderRecord> findListByOrderNumber(String orderNumber) {
		return template.selectList("TrafficOrderRecord.findListByOrderNumber",
				orderNumber);
	}
	
	@Override
	public int insert(TrafficOrderRecord orderRecord) {
		return template.insert("TrafficOrderRecord.insert", orderRecord);
	}
	
}
