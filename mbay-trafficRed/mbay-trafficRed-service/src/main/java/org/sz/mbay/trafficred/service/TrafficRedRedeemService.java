package org.sz.mbay.trafficred.service;

import org.sz.mbay.trafficred.bean.TrafficRedPackage;
import org.sz.mbay.trafficred.result.Result;

/**
 * 兑换服务
 * 
 * @author jerry
 */
public interface TrafficRedRedeemService {
	
	
	/**
	 * 兑换流量操作
	 * @param campaignId 活动ID
	 * @param pack 兑换流量包
	 * @param mobile 手机号码
	 * @return
	 */
	Result operateTraffic(long campaignId, TrafficRedPackage pack, String mobile);
	
	
	/**
	 * 兑换MB操作
	 * @param campaignId 活动ID
	 * @param mbay MB大小
	 * @param mobile 手机号码
	 * @return
	 */
	Result operateMbay(long campaignId, Integer mbay, String mobile);
	
}
