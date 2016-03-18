package org.sz.mbay.channel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.sz.mbay.channel.service.TrafficBuyNowService;
import org.sz.mbay.channel.user.service.UserAccountService;
import org.sz.mbay.trafficorder.service.TrafficOrderService;
import org.sz.mbay.trafficrecharge.service.TrafficRechargeService;

public class TrafficBuyNowServiceImpl implements TrafficBuyNowService {

	@Autowired
	TrafficRechargeService rechargeService;

	@Autowired
	UserAccountService userAccountService;
	
	@Autowired
	TrafficOrderService orderService;
	@Override
	public void rechargeTrffic(String orderNumber,String userNumber) {
	
		
	}

}
