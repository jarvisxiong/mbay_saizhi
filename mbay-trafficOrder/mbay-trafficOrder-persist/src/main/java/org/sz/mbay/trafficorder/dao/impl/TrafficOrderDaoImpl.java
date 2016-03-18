package org.sz.mbay.trafficorder.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.dao.TrafficOrderDao;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderStatus;

@Repository
public class TrafficOrderDaoImpl extends BaseDaoImpl<TrafficOrder>
		implements TrafficOrderDao {
		
	@Override
	public int updateOrderStatus(String orderNumber,
			TrafficOrderStatus orderstatus, OperatorRechargeStatus ops) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderStatus", orderstatus);
		map.put("ops", ops);
		map.put("orderNumber", orderNumber);
		return this.template.update("updateTrafficOrderStatus",map);
	}
	
	@Override
	public List<TrafficOrder> findTrafficOrderByRelateNumber(
			String relateNumber) {
		return this.template.selectList("findTrafficOrderByRelateNumber",relateNumber);
	}

	@Override
	public void setOrderOperatorNumber(String orderNumber, String operatorNumber) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderNumber", orderNumber);
		map.put("operatorNumber", operatorNumber);
		 this.template.update("setOrderOperatorNumber",map);
		
	}

	@Override
	public String findOrderNumberByOperatorNumber(String operatorNumber) {
		return this.template.selectOne("findOrderNumberByOperatorNumber", operatorNumber);
	}
	
}
