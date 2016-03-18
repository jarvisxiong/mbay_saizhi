package org.sz.mbay.trafficorder.dao.impl;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.trafficorder.bean.TrafficOrderRefund;
import org.sz.mbay.trafficorder.dao.TrafficOrderRefundDao;

@Repository
public class TrafficOrderRefundDaoImpl extends BaseDaoImpl<TrafficOrderRefund>
		implements TrafficOrderRefundDao {
		
	@Override
	public TrafficOrderRefund findByOrderNumber(String orderNumber) {
		return template.selectOne("TrafficOrderRefund.findByOrderNumber",
				orderNumber);
	}
	
	@Override
	public int create(TrafficOrderRefund refund) {
		return template.insert("TrafficOrderRefund.create", refund);
		
	}
	
	@Override
	public int updateByOrderNumberSelective(TrafficOrderRefund bean) {
		return template.update(
				"TrafficOrderRefund.updateByOrderNumberSelective", bean);
	}
	
}
