package org.sz.mbay.trafficred.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.dao.utils.PKgen;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.dao.TrafficRedExchangeRecordDao;
import org.sz.mbay.trafficred.enums.RedeemType;
import org.sz.mbay.trafficred.enums.SharkCycleType;
import org.sz.mbay.trafficred.service.TrafficRedExchangeRecordService;

@Service
public class TrafficRedExchangeRecordServiceImpl extends BaseServiceImpl
		implements TrafficRedExchangeRecordService {
	
	@Autowired
	private TrafficRedExchangeRecordDao exchangeRecordDao;
	
	@Override
	public boolean checkMobileEnterable(String campaignNumber, String mobile,
			SharkCycleType type, int times) {
		return exchangeRecordDao.checkMobileEnterable(campaignNumber, mobile,
				type, times);
	}
	
	@Override
	public TrafficRedExchangeRecord createRecord(TrafficRedExchangeRecord record) {
		record.setId(PKgen.getInstance().nextPK());
		return exchangeRecordDao.createBean(record);
	}
	
	@Override
	public void updateRecord(TrafficRedExchangeRecord record) {
		exchangeRecordDao.updateRecord(record);
	}
	
	@Override
	public List<TrafficRedExchangeRecord> findByCNumber(PageInfo pageInfo,
			String cnumber) {
		return this.exchangeRecordDao.findList(cnumber, pageInfo, "ByCNumber");
	}
	
	@Override
	public List<TrafficRedExchangeRecord> findByContion(PageInfo pageInfo,
			String cnumber, String mobile, DateTime start, DateTime end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cnumber", cnumber);
		map.put("mobile", mobile);
		map.put("start", start);
		map.put("end", end);
		return this.exchangeRecordDao.findList(map, pageInfo, "ByCondition");
	}
	
	@Override
	public TrafficRedExchangeRecord findById(Long recordId) {
		return exchangeRecordDao.selectById(recordId);
	}
	
	@Override
	public int getParticipatedTimes(String number, String mobile,
			SharkCycleType type) {
		return exchangeRecordDao.getParticipatedTimes(number, mobile, type);
	}

	@Override
	public void createNoWinRecord(String number, String mobile) {
		TrafficRedExchangeRecord record = new TrafficRedExchangeRecord();
		record.setCampaignNumber(number);
		record.setTime(DateTime.now());
		record.setMobile(mobile);
		record.setRedeemType(RedeemType.NON);
		record.setContent("未中奖");
		record.setSize(0D);
		createRecord(record);
	}
}
