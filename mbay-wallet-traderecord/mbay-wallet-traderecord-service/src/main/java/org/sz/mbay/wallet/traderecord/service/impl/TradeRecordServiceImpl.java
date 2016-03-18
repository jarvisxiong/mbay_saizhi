//package org.sz.mbay.wallet.traderecord.service.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.sz.mbay.base.pagehelper.PageInfo;
//import org.sz.mbay.base.service.impl.BaseServiceImpl;
//import org.sz.mbay.wallet.traderecord.bean.TradeRecord;
//import org.sz.mbay.wallet.traderecord.dao.TradeRecordDao;
//import org.sz.mbay.wallet.traderecord.qo.TradeRecordQO;
//import org.sz.mbay.wallet.traderecord.service.TradeRecordService;
//
//@Service
//public class TradeRecordServiceImpl extends BaseServiceImpl
//		implements TradeRecordService {
//		
//	@Autowired
//	private TradeRecordDao tradeRecordDao;
//	
//	@Override
//	public List<TradeRecord> findPage(TradeRecordQO tradeRecordQO,
//			PageInfo pageInfo) {
//		return tradeRecordDao.findList(tradeRecordQO, pageInfo, "TradeRecord");
//	}
//	
//	@Override
//	public boolean create(TradeRecord record) {
//		return tradeRecordDao.insert(record) == 1;
//	}
//	
//	@Override
//	public boolean isRecordExsit(String snumber) {
//		return tradeRecordDao.isRecordExsit(snumber);
//	}
//	
//	@Override
//	public TradeRecord findBySerialNumber(String serialNumber) {
//		return tradeRecordDao.findBySerialNumber(serialNumber);
//	}
//	
//	@Override
//	public boolean updateByIdSelective(TradeRecord recordup) {
//		return tradeRecordDao.updateByIdSelective(recordup) == 1;
//	}
//}
