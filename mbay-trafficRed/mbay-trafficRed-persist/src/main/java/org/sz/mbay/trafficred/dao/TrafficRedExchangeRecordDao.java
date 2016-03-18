package org.sz.mbay.trafficred.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.enums.SharkCycleType;

public interface TrafficRedExchangeRecordDao extends
		BaseDao<TrafficRedExchangeRecord> {
	
	boolean checkMobileEnterable(String campaignNumber, String mobile,
			SharkCycleType type, int times);
	
	void updateRecord(TrafficRedExchangeRecord record);
	
	TrafficRedExchangeRecord selectById(Long recordId);
	
	int getParticipatedTimes(String number, String mobile, SharkCycleType type);
	
}
