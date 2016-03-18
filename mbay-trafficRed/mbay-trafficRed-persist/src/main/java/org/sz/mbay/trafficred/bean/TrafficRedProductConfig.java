package org.sz.mbay.trafficred.bean;

import java.io.Serializable;

import org.sz.mbay.trafficred.enums.ProductType;

public class TrafficRedProductConfig implements Serializable {
	
	public static final int TRAFFIC_UNLIMIT = -1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1929847423672605684L;
	
	public TrafficRedProductConfig() {
	}
	
	public TrafficRedProductConfig(ProductType productType) {
		this.productType = productType;
	}
	
	/**
	 * 活动ID
	 */
	private long campaignId;
	
	/**
	 * 产品类型
	 */
	private ProductType productType;
	
	/**
	 * 预设总量
	 */
	private double poolSize;
	
	/**
	 * 剩余
	 */
	private double poolRemain;
	
	/**
	 * 单日上限
	 */
	private double dailyLimit;
	
	/**
	 * 当日剩余
	 */
	private double dailyRemain;
	
	/**
	 * 告警阀值
	 */
	private int threshold;
	
	/**
	 * 是否已告警提醒
	 */
	private boolean thresholdWarned;
	
	public long getCampaignId() {
		return campaignId;
	}
	
	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}
	
	public ProductType getProductType() {
		return productType;
	}
	
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	
	public double getPoolSize() {
		return poolSize;
	}
	
	public void setPoolSize(double poolSize) {
		this.poolSize = poolSize;
	}
	
	public double getDailyLimit() {
		return dailyLimit;
	}
	
	public void setDailyLimit(double dailyLimit) {
		this.dailyLimit = dailyLimit;
	}
	
	public double getDailyRemain() {
		return dailyRemain;
	}
	
	public void setDailyRemain(double dailyRemain) {
		this.dailyRemain = dailyRemain;
	}
	
	public double getPoolRemain() {
		return poolRemain;
	}
	
	public void setPoolRemain(double poolRemain) {
		this.poolRemain = poolRemain;
	}
	
	public int getThreshold() {
		return threshold;
	}
	
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	public boolean isThresholdWarned() {
		return thresholdWarned;
	}
	
	public void setThresholdWarned(boolean thresholdWarned) {
		this.thresholdWarned = thresholdWarned;
	}
	
}
