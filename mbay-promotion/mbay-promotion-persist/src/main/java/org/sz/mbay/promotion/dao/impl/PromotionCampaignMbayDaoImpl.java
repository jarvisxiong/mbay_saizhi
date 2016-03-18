package org.sz.mbay.promotion.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.promotion.bean.PromotionCampaignMbay;
import org.sz.mbay.promotion.dao.PromotionCampaignMbayDao;

@Repository
public class PromotionCampaignMbayDaoImpl extends
		BaseDaoImpl<PromotionCampaignMbay>
		implements PromotionCampaignMbayDao {
		
	@Override
	public List<PromotionCampaignMbay> findByCampaignNumber(
			String campaignNumber) {
		return this.template.selectList(
				"findPromotionCampaignMbayByCampaignNumber", campaignNumber);
	}
	
	@Override
	public int deleteCampaignMbayProduct(String campaignNumber,
			long productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("campaignNumber", campaignNumber);
		map.put("mbayId", productId);
		return template.delete("deletePromotionCampaignMbayProduct", map);
	}
	
	@Override
	public PromotionCampaignMbay findValidMin(String campaignNumber) {
		return template.selectOne("findPromotionCampaignMbayValidMin",
				campaignNumber);
	}
}
