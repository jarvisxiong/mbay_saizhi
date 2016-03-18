package org.sz.mbay.traffic.operators;

import org.sz.mbay.operator.enums.TrafficType;
import org.sz.mbay.traffic.response.ChargeResult;

/**
 * 流量兑换接口
 * 
 * @author ONECITY
 * 
 */
public interface TrafficRecharge {
	
	
	/**
	 * @param mobile 手机号
	 * @param traffictype 流量类型
	 * @param traffic  流量大小
	 * @return
	 */
	public ChargeResult charge(String mobile,TrafficType traffictype, int traffic,String orderNumber);
	
	
	
	public boolean isSupportTraffic(TrafficType ttype, int traffic);
}
