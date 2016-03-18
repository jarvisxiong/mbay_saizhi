package org.sz.mbay.trafficorder.dao;

import java.util.List;

import org.sz.mbay.base.dao.BaseDao;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderStatus;

public interface TrafficOrderDao extends BaseDao<TrafficOrder> {
	
	public int updateOrderStatus(String orderNumber,
			TrafficOrderStatus orderstatus, OperatorRechargeStatus ops);
			
	public List<TrafficOrder> findTrafficOrderByRelateNumber(
			String relateNumber);
	
	
	public void setOrderOperatorNumber(String orderNumber, String operatorNumber);
	
	public String findOrderNumberByOperatorNumber(String operatorNumber);
	
	
}
