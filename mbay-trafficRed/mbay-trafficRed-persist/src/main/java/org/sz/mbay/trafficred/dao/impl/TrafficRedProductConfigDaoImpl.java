package org.sz.mbay.trafficred.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.bean.TrafficRedProductConfig;
import org.sz.mbay.trafficred.dao.TrafficRedProductConfigDao;
import org.sz.mbay.trafficred.enums.ProductType;

@Repository
public class TrafficRedProductConfigDaoImpl
		extends BaseDaoImpl<TrafficRedProductConfig>
		implements TrafficRedProductConfigDao {
		
	@Override
	public int create(TrafficRedProductConfig config) {
		return template.insert("TrafficRedProductConfig.create", config);
	}
	
	@Override
	public void resetDailyRemain() {
		template.update("TrafficRedProductConfig.resetDailyRemain");
	}
	
	@Override
	public boolean isSelectedProduct(long campaignId, ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		return template.selectOne("TrafficRedProductConfig.isSelectedProduct",
				map);
	}
	
	@Override
	public int increaseProductPoolSize(long campaignId, ProductType productType,
			double increasePool) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		map.put("mbay", increasePool);
		return template
				.update("TrafficRedProductConfig.increaseProductPoolSize", map);
	}
	
	@Override
	public boolean isProductPoolRemainLessThanThreshold(long campaignId,
			ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		return template.selectOne(
				"TrafficRedProductConfig.isProductPoolRemainLessThanThreshold",
				map);
	}
	
	@Override
	public void resetProductThsholdWarnStatus(long campaignId,
			ProductType productType) {
		updateProductThresholdStatus(campaignId, productType, false);
	}
	
	private int updateProductThresholdStatus(long campaignId,
			ProductType productType, boolean status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		map.put("warnStatus", status);
		return template.update(
				"TrafficRedProductConfig.updateProductThresholdStatus", map);
	}
	
	@Override
	public boolean changeProductThresholdWarned(long campaignId,
			ProductType productType) {
		return updateProductThresholdStatus(campaignId, productType,
				true) == 1;
	}
	
	@Override
	public boolean updateProductThreshold(long campaignId,
			ProductType productType, int threshold) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		map.put("threshold", threshold);
		return this.template.update(
				"TrafficRedProductConfig.updateProductThreshold", map) == 1;
	}
	
	@Override
	public int reduceProductPoolRemain(long campaignId, ProductType productType,
			double mbay) {
		if (mbay == 0)
			return 0;
		if (mbay < 0)
			mbay = -mbay;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		map.put("mbay", mbay);
		return this.template.update(
				"TrafficRedProductConfig.reduceProductPoolRemain", map);
	}
	
	@Override
	public double findProductDailyLimit(long campaignId,
			ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		return template.selectOne(
				"TrafficRedProductConfig.findProductDailyLimit", map);
	}
	
	@Override
	public int reduceProductDailyRemain(long campaignId,
			ProductType productType, double mbay) {
		if (mbay == 0)
			return 0;
		if (mbay < 0)
			mbay = -mbay;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		map.put("mbay", mbay);
		return template.update(
				"TrafficRedProductConfig.reduceProductDailyRemain", map);
	}
	
	@Override
	public boolean updateProductDailyLimit(long campaignId,
			ProductType productType, double dailyLimit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType.ordinal());
		map.put("dailyLimit", dailyLimit);
		map.put("rowcount", 0);
		template.update("TrafficRedProductConfig.updateProductDailyLimit", map);
		return Integer.valueOf(map.get("rowcount").toString()) == 1;
	}
	
	@Override
	public TrafficRedProductConfig findTrafficRedProductConfig(long campaignId,
			ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		return template.selectOne(
				"TrafficRedProductConfig.findTrafficRedProductConfig", map);
	}
	
	@Override
	public ThresholdWarnInfo findThresholdWarnInfo(long campaignId,
			ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignId", campaignId);
		map.put("productType", productType);
		return template.selectOne(
				"TrafficRedProductConfig.findThresholdWarnInfo", map);
	}
	
	@Override
	public int clearConfig(Long id) {
		return template.update("TrafficRedProductConfig.clearConfig", id);
	}
}
