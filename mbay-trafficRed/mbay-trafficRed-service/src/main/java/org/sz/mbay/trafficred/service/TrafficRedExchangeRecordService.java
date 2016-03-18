package org.sz.mbay.trafficred.service;

import java.util.List;

import org.joda.time.DateTime;
import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.trafficred.bean.TrafficRedExchangeRecord;
import org.sz.mbay.trafficred.enums.SharkCycleType;

/**
 * 
 * @author Fenlon
 * 
 */
public interface TrafficRedExchangeRecordService {
	
	/**
	 * 检测号码是否超出限制
	 * 
	 * @param campaignNumber
	 * @param mobile
	 * @return
	 */
	boolean checkMobileEnterable(String campaignNumber, String mobile,
			SharkCycleType type, int times);
	
	/**
	 * 创建记录
	 * 
	 * @param record
	 * @return
	 */
	TrafficRedExchangeRecord createRecord(TrafficRedExchangeRecord record);
	
	/**
	 * 更新记录
	 * 
	 * @param record
	 */
	void updateRecord(TrafficRedExchangeRecord record);
	
	/**
	 * 根据活动编号查找活动记录
	 * 
	 * @param pageInfo
	 * @param cnumber
	 * @return
	 */
	List<TrafficRedExchangeRecord> findByCNumber(PageInfo pageInfo,
			String cnumber);
	
	/**
	 * 根据条件查询活动记录
	 * 
	 * @param pageInfo
	 *            分页信息
	 * @param cnumber
	 *            活动编号
	 * @param mobile
	 *            手机号
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	List<TrafficRedExchangeRecord> findByContion(PageInfo pageInfo,
			String cnumber, String mobile, DateTime start, DateTime end);
	
	/**
	 * 查找记录
	 * 
	 * @param recordId
	 */
	TrafficRedExchangeRecord findById(Long recordId);
	
	/**
	 * 获取已参与次数
	 * 
	 * @param number
	 * @param mobile
	 * @param type
	 * @return
	 */
	int getParticipatedTimes(String number, String mobile, SharkCycleType type);
	
	/**
	 * 创建未中奖记录
	 * 
	 * @param number
	 * @param mobile
	 */
	void createNoWinRecord(String number, String mobile);
	
}
