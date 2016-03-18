package org.sz.mbay.wallet.constant.error;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

/**
 * TrafficRedeemAction 相应错误
 * 
 * @author jerry
 */
public class TrafficRedeemError {
	
	public static Response TRAFFIC_REDEEM_FAILED = ResponseFail.create(
			"TRAFFIC_REDEEM_FAILED", "流量充值失败");
}
