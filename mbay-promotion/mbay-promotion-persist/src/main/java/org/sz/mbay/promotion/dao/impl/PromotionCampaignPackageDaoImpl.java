package org.sz.mbay.promotion.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.operator.enums.OperatorType;
import org.sz.mbay.promotion.bean.PromotionCampaignPackage;
import org.sz.mbay.promotion.dao.PromotionCampaignPackageDao;

@Repository
public class PromotionCampaignPackageDaoImpl extends
		BaseDaoImpl<PromotionCampaignPackage>
		implements PromotionCampaignPackageDao {
		
	@Override
	public List<PromotionCampaignPackage> findByCampaignNumber(
			String campaignNumber) {
		return this.template.selectList(
				"findPromotionCampaignPackageByCampaignNumber", campaignNumber);
	}
	
	@Override
	public int deleteCampaignTrafficProduct(String campaignNumber,
			long productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("packageId", productId);
		return template.delete("deletePromotionCampaignTrafficProduct", map);
	}
	
	@Override
	public PromotionCampaignPackage findValidMin(String campaignNumber,
			OperatorType type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("campaignNumber", campaignNumber);
		param.put("operatorType", type);
		return template.selectOne("findPromotionCampaignPackageValidMin",
				param);
	}
	
	@Override
	public List<PromotionCampaignPackage> findByCampaignNumberAndOperatorType(
			String campaignNumber, OperatorType type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("campaignNumber", campaignNumber);
		param.put("operatorType", type);
		return this.template.selectList(
				"findPromotionCampaignPackageByCampaignNumberAndOperatorType",
				param);
	}
}
