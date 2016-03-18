package org.sz.mbay.trafficrecharge.service;

import org.sz.mbay.base.service.BaseService;
import org.sz.mbay.base.wrap.ExecuteResult;

public interface TrafficRechargeService extends BaseService {
	
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
	public ExecuteResult recharge(String orderNumber);
	
	
}
