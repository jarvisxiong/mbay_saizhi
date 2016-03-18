package org.sz.mbay.trafficorder.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficorder.bean.TrafficOrderRecord;

public interface TrafficOrderRecordDao extends BaseDao<TrafficOrderRecord> {
	
	List<TrafficOrderRecord> findListByOrderNumber(String orderNumber);

	int insert(TrafficOrderRecord orderRecord);
	
}
