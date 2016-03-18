package org.sz.mbay.trafficred.service;

import org.sz.mbay.trafficred.bean.TrafficRedAdvancedConfig;

public interface TrafficRedAdvancedService {
	
	/**
	 * 创建流量红包开发者模式配置信息
	 * @param config
	 */
	public void createAdvanceConfig(long campaignId);
	
	/**
	 * 查询开发者模式配置信息
	 * @param campaginId
	 * @return
	 */
	public TrafficRedAdvancedConfig findAdvancedConfig(long campaginId);
	
	/**
	 * 重置key
	 * @param campaginId
	 * @return 重置后的key
	 */
	public String resetKey(long campaginId);
	
	
	
	/**
	 * 启用流量红包开发者模式
	 * @param campaignId
	 */
	public void disableTrafficRedAdvanced(long campaignId);
	
	/**
	 * 禁用流量红包开发者模式
	 * @param campaignId
	 */
	public void enabledTrafficRedAdvanced(long campaignId);
	
}
