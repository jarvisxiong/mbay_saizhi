package org.sz.mbay.promotion.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.promotion.bean.PromotionProductConfig;
import org.sz.mbay.promotion.dao.PromotionProductConfigDao;
import org.sz.mbay.trafficred.bean.ThresholdWarnInfo;
import org.sz.mbay.trafficred.enums.ProductType;

@Repository
public class PromotionProductConfigDaoImpl extends
		BaseDaoImpl<PromotionProductConfig>
		implements PromotionProductConfigDao {
		
	@Override
	public PromotionProductConfig findProductConfig(String campaignNumber,
			ProductType type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("type", type);
		return this.template.selectOne(
				"findPromotionProductConfigByCampaignNumberAndProductType",
				map);
	}
	
	@Override
	public boolean updateProductDailyLimit(String campaignNumber,
			ProductType productType, double dailyLimit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType.ordinal());
		map.put("dailyLimit", dailyLimit);
		map.put("rowcount", 0);
		template.update("updatePromotionProductDailyLimit", map);
		return Integer.valueOf(map.get("rowcount").toString()) == 1;
	}
	
	@Override
	public boolean isSelectedProduct(String campaignNumber,
			ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		return template.selectOne("isSelectedPromotionProduct", map);
	}
	
	@Override
	public int increaseProductPoolSize(String campaignNumber,
			ProductType productType,
			double increasePool) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		map.put("mbay", increasePool);
		return template.update("increasePromotionProductPoolSize", map);
	}
	
	@Override
	public boolean isProductPoolRemainLessThanThreshold(String campaignNumber,
			ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		return template.selectOne(
				"isPromotionProductPoolRemainLessThanThreshold", map);
	}
	
	@Override
	public void resetProductThsholdWarnStatus(String campaignNumber,
			ProductType productType) {
		updateProductThresholdStatus(campaignNumber, productType, false);
	}
	
	private int updateProductThresholdStatus(String campaignNumber,
			ProductType productType, boolean status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		map.put("warnStatus", status);
		return template.update("updatePromotionProductThresholdStatus", map);
	}
	
	@Override
	public boolean updateProductThreshold(String campaignNumber,
			ProductType productType, int threshold) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		map.put("threshold", threshold);
		return this.template.update("updatePromotionProductThreshold",
				map) == 1;
	}
	
	@Override
	public int reduceProductPoolRemain(String campaignNumber,
			ProductType productType,
			double mbay) {
		if (mbay == 0)
			return 0;
		if (mbay < 0)
			mbay = -mbay;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		map.put("mbay", mbay);
		return this.template.update("reducePromotionProductPoolRemain", map);
	}
	
	@Override
	public int reduceProductDailyRemain(String campaignNumber,
			ProductType productType, double mbay) {
		if (mbay == 0)
			return 0;
		if (mbay < 0)
			mbay = -mbay;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		map.put("mbay", mbay);
		return template.update("reducePromotionProductDailyRemain", map);
	}
	
	@Override
	public double findProductDailyLimit(String campaignNumber,
			ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		return template.selectOne("findPromotionProductDailyLimit", map);
	}
	
	@Override
	public ThresholdWarnInfo findThresholdWarnInfo(String campaignNumber,
			ProductType productType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("productType", productType);
		return template.selectOne("findPromotionThresholdWarnInfo", map);
	}
	
	@Override
	public boolean changeProductThresholdWarned(String campaignNumber,
			ProductType productType) {
		return updateProductThresholdStatus(campaignNumber, productType,
				true) == 1;
	}
	
	@Override
	public void resetDailyRemain() {
		template.update("PromotionProductConfig.resetDailyRemain");
	}
	
}
