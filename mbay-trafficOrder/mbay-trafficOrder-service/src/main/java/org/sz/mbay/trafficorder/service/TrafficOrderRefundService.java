package org.sz.mbay.trafficorder.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.wrap.ExecuteResult;
import org.sz.mbay.trafficorder.bean.TrafficOrderRefund;
import org.sz.mbay.trafficorder.qo.TrafficOrderRefundQO;

/**
 * 流量订单退款记录
 * 
 * @author jerry
 */
public interface TrafficOrderRefundService {
	
	/**
	 * 创建记录
	 * 
	 * @param refund
	 */
	void create(TrafficOrderRefund refund);
	
	/**
	 * 查找退款信息
	 * 
	 * @param orderNumber
	 * @return
	 */
	TrafficOrderRefund findByOrderNumber(String orderNumber);
	
	/**
	 * 分页查询
	 * 
	 * @param form
	 * @param pageInfo
	 * @return
	 */
	List<TrafficOrderRefund> findList(TrafficOrderRefundQO form,
			PageInfo pageInfo);
			
	/**
	 * 退款申请审核通过
	 * 
	 * @param refund
	 * @return
	 */
	ExecuteResult refundPass(TrafficOrderRefund refund);
	
	/**
	 * 退款申请审核不通过
	 * 
	 * @param refund
	 * @return
	 */
	ExecuteResult refundUnpass(TrafficOrderRefund refund);
	
}
