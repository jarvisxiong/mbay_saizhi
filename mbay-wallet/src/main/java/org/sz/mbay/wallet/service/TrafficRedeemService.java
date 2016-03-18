package org.sz.mbay.wallet.service;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.operator.bean.TrafficPackage;

/**
 * 流量充值服务层
 * 
 * @author jerry
 */
public interface TrafficRedeemService {
	
	/**
	 * 流量充值
	 * 
	 * @param order
	 * @return
	 */
	Response trafficExchange(String mobile, TrafficPackage pack, String desc);
}
