package org.sz.mbay.trafficred.service;

import org.sz.mbay.trafficred.bean.TrafficRedMbayGiftLimitConfig;

public interface TrafficRedMbayGiftLimitService {
	
	public TrafficRedMbayGiftLimitConfig find();
	
	public boolean updateById(TrafficRedMbayGiftLimitConfig config);
	
	public boolean updateByIdSelective(TrafficRedMbayGiftLimitConfig config);
	
}
