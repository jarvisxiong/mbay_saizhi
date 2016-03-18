package org.sz.mbay.traffic.service;

import org.sz.mbay.traffic.response.TrafficResponse;

/**
 * @author ONECITY
 * 兑换码兑换流量Service
 *
 */
public interface CodeRedeemService {
	
	public TrafficResponse redeem(String redeemcode,String mobile, int area,int operator);

}
