package org.sz.mbay.trafficred.redeem.strategy;

import org.sz.mbay.trafficred.redeem.traffic.RedeemTraffic;
import org.sz.mbay.trafficred.result.Result;

/**
 * 流量必中策略
 * 
 * @author jerry
 */
public class TrafficMustHit {
	
	/**
	 * 执行策略
	 * 
	 * @param campaign
	 * @param mobile
	 * @return
	 */
	public static Result process(long campaignId, String mobile) {
		return RedeemTraffic.redeem(campaignId, mobile);
	}
}
