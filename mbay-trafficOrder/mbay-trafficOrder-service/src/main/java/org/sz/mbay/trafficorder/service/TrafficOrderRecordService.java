package org.sz.mbay.trafficorder.service;

import java.util.List;

import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.trafficorder.bean.TrafficOrderRecord;

public interface TrafficOrderRecordService extends BaseService {
	
	/**
	 * 更具订单号和状态查询对应
	 * 
	 * @param ordernumber
	 * @param status
	 * @return
	 */
	public List<TrafficOrderRecord> findListByOrderNumber(String orderNumber);
	
	/**
	 * 创建实体
	 * 
	 * @param orderRecord
	 */
	public boolean create(TrafficOrderRecord orderRecord);
}
