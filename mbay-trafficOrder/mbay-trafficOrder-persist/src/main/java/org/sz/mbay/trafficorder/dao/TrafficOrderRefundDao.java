package org.sz.mbay.trafficorder.dao;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficorder.bean.TrafficOrderRefund;

/**
 * 流量充值退款
 * 
 * @author jerry
 */
public interface TrafficOrderRefundDao extends BaseDao<TrafficOrderRefund> {
	
	TrafficOrderRefund findByOrderNumber(String orderNumber);

	int create(TrafficOrderRefund refund);

	int updateByOrderNumberSelective(TrafficOrderRefund bean);
	
}
