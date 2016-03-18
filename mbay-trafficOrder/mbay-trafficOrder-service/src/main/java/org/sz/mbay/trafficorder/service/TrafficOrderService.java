package org.sz.mbay.trafficorder.service;

import java.util.List;

import org.sz.mbay.base.pagehelper.PageInfo;
import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.trafficorder.bean.TrafficOrder;
import org.sz.mbay.trafficorder.bean.TrafficRechargeInfo;
import org.sz.mbay.trafficorder.enums.OperatorRechargeStatus;
import org.sz.mbay.trafficorder.enums.TrafficOrderStatus;
import org.sz.mbay.trafficorder.qo.TrafficOrderQO;

public interface TrafficOrderService extends BaseService {
	
	/**
	 * @param mobile
	 *            手机号
	 * @param trafficPackageNumber
	 *            流量包编号
	 * @param relationNumber
	 *            关联单号
	 * @param userNumber
	 *            用户编号
	 * @return
	 */
	public String create(TrafficRechargeInfo rechargeInfo);
	
	/**
	 *
	 * 
	 * @param orderNumber
	 * @param userNumber
	 * @return
	 */
	public boolean isExistOrder(String orderNumber, String userNumber);
	
	/**
	 * 查询流量充值订单
	 * 
	 * @param orderQO
	 *            qo
	 * @param pageinfo
	 *            分页信息
	 * @return
	 */
	public List<TrafficOrder> findAllTrafficOrder(
			TrafficOrderQO orderQO, PageInfo pageinfo);
			
	/**
	 * 更具订单号查询订单
	 * 
	 * @param ordernumber
	 * @return
	 */
	public TrafficOrder findTrafficOrder(String orderNumber);
	
	/**
	 * 修改订单状态和运营商流量充值状态
	 * 
	 * @param orderstatus
	 *            订单状态 为NULL则不修改
	 * @param ops
	 *            运营商操作状态 为NULL则不修改
	 * @return
	 */
	public void updateOrderStatus(String orderNumber,
			TrafficOrderStatus orderstatus, OperatorRechargeStatus ops,String description);	
	
	public void updateOrderStatusByOperatorNumber(String operatorNumber,
			TrafficOrderStatus orderstatus, OperatorRechargeStatus ops,String description);	
	
	
	/**
	 * 设置订单运营商订单号
	 * @param orderNumber 订单号
	 * @param operatorNumber 运营商流量充值单号
	 */
	public void setOrderOperatorNumber(String orderNumber,String operatorNumber);
	/**
	 * 根据活动编号查找任意一条记录的基本信息
	 * 
	 * @param campaignNumber
	 * @return
	 */
	public List<TrafficOrder> findTrafficOrderByRelateNumber(
			String relateNumber);
			
}
