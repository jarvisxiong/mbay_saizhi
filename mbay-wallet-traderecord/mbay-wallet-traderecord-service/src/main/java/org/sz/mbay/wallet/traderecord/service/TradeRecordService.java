//package org.sz.mbay.wallet.traderecord.service;
//
//import java.util.List;
//
//import org.sz.mbay.base.pagehelper.PageInfo;
//import org.sz.mbay.wallet.traderecord.bean.TradeRecord;
//import org.sz.mbay.wallet.traderecord.qo.TradeRecordQO;
//
///**
// * 交易明细服务层
// * 
// * @author jerry
// */
//public interface TradeRecordService {
//	
//	/**
//	 * 分页获取交易记录
//	 * 
//	 * @param tradeRecord
//	 * @param object
//	 * @return
//	 */
//	List<TradeRecord> findPage(TradeRecordQO tradeRecordQO, PageInfo pageInfo);
//	
//	/**
//	 * 创建交易记录
//	 * 
//	 * @param createTrafficRedRecord
//	 * @return
//	 */
//	boolean create(TradeRecord record);
//	
//	/**
//	 * 检测记录是否存在
//	 * 
//	 * @param snumber
//	 * @return
//	 */
//	boolean isRecordExsit(String snumber);
//	
//	/**
//	 * 查找记录
//	 * 
//	 * @param serialNumber
//	 * @return
//	 */
//	TradeRecord findBySerialNumber(String serialNumber);
//	
//	/**
//	 * 更新
//	 * 
//	 * @param recordup
//	 */
//	boolean updateByIdSelective(TradeRecord recordup);
//}
