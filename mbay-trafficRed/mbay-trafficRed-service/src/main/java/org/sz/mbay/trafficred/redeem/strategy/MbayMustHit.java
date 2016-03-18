package org.sz.mbay.trafficred.redeem.strategy;

import org.sz.mbay.trafficred.redeem.mb.RedeemMbay;
import org.sz.mbay.trafficred.result.Result;

/**
 * 美贝必中策略
 * 
 * @author jerry
 */
public class MbayMustHit {
	
	
	/**
	 * 执行策略
	 * @param campaignId 活动ID
	 * @param mobile 手机号
	 * @return
	 */
	public static Result process(long campaignId, String mobile) {
		return RedeemMbay.redeem(campaignId, mobile);
	}
}
