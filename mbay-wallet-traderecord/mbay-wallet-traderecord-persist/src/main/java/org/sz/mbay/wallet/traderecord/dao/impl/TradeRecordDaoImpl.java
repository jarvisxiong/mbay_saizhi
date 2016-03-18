//package org.sz.mbay.wallet.traderecord.dao.impl;
//
//import org.springframework.stereotype.Repository;
//import org.sz.mbay.base.dao.impl.BaseDaoImpl;
//import org.sz.mbay.base.dao.utils.PKgen;
//import org.sz.mbay.wallet.traderecord.bean.TradeRecord;
//import org.sz.mbay.wallet.traderecord.dao.TradeRecordDao;
//
//@Repository
//public class TradeRecordDaoImpl extends BaseDaoImpl<TradeRecord> implements TradeRecordDao {
//
//	@Override
//	public int insert(TradeRecord tradeRecord) {
//		tradeRecord.setId(PKgen.getInstance().nextPK());
//		return template.insert("TradeRecord.insert", tradeRecord);
//	}
//
//	@Override
//	public boolean isRecordExsit(String snumber) {
//		return template.selectOne("TradeRecord.isRecordExsit", snumber);
//	}
//
//	@Override
//	public TradeRecord findBySerialNumber(String serialNumber) {
//		return template.selectOne("TradeRecord.findBySerialNumber", serialNumber);
//	}
//
//	@Override
//	public int updateByIdSelective(TradeRecord recordup) {
//		return template.update("TradeRecord.updateByIdSelective", recordup);
//	}
//}
