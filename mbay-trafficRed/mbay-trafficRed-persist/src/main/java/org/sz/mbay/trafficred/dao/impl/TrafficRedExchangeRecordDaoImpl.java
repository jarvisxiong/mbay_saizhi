package org.sz.mbay.trafficred.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.dao.TrafficRedExchangeRecordDao;
import org.sz.mbay.trafficred.enums.SharkCycleType;

@Repository
public class TrafficRedExchangeRecordDaoImpl extends
		BaseDaoImpl<TrafficRedExchangeRecord> implements
		TrafficRedExchangeRecordDao {
	
	@Override
	public boolean checkMobileEnterable(String campaignNumber, String mobile,
			SharkCycleType type, int times) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("campaignNumber", campaignNumber);
		param.put("mobile", mobile);
		param.put("type", type);
		param.put("times", times);
		return template.selectOne(
				"TrafficRedExchangeRecord.checkMobileEnterable", param);
	}
	
	@Override
	public void updateRecord(TrafficRedExchangeRecord record) {
		template.update(
				"TrafficRedExchangeRecord.updateTrafficRedExchangeRecord",
				record);
	}
	
	@Override
	public TrafficRedExchangeRecord selectById(Long recordId) {
		return template.selectOne("TrafficRedExchangeRecord.selectById",
				recordId);
	}
	
	@Override
	public int getParticipatedTimes(String number, String mobile,
			SharkCycleType type) {
		Map<String, Object> params = new HashMap<>();
		params.put("campaignNumber", number);
		params.put("mobile", mobile);
		params.put("type", type);
		return template.selectOne(
				"TrafficRedExchangeRecord.getParticipatedTimes", params);
	}
	
}
