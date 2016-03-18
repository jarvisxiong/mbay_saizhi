package org.sz.mbay.traffic.service;

import org.sz.mbay.operator.bean.TrafficPackage;
import org.sz.mbay.traffic.response.TrafficResponse;
import org.sz.mbay.traffic.util.Area;

/**
 * 美贝兑换流量Service
 * 
 * @author ONECITY
 * 
 */
public interface TrafficChargeService {
	/**
	 * @param ordernumber 订单号
	 * @param mobile 流量充值手机号码
	 * @param area   手机号归属地
	 * @param operator 手机号归属通信运营商
	 * @param flowtype 充值流量类型
	 * @param redeemflow 充值流量大小
	 * @return
	 */
	public TrafficResponse recharge(String mobile,
			Area area, TrafficPackage trafficPackage,String notifyURL,String ext );

}
