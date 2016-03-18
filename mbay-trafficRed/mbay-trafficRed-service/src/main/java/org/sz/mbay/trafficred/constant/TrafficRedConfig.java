package org.sz.mbay.trafficred.constant;

import org.sz.mbay.base.config.ResourceConfig;

/**
 * 流量红包配置
 * 
 * @author jerry
 */
public class TrafficRedConfig {
	
	/**
	 * 流量红包送人领取活动结束跳转内定页面
	 */
	public static final String CAMPAIGN_OVER_URL = ResourceConfig
			.getProperty("campaign_over_URL");
			
	/**
	 * 流量红包域名
	 */
	public static final String TRAFFIC_RED_DOMAIN = ResourceConfig
			.getProperty("trafficRedDomain");
			
}
