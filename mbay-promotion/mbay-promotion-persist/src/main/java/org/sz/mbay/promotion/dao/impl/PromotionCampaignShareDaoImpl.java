package org.sz.mbay.promotion.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sz.mbay.base.dao.impl.BaseDaoImpl;
import org.sz.mbay.promotion.bean.PromotionCampaignShare;
import org.sz.mbay.promotion.dao.PromotionCampaignShareDao;

@Repository
public class PromotionCampaignShareDaoImpl
		extends BaseDaoImpl<PromotionCampaignShare>
		implements PromotionCampaignShareDao {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionCampaignShareDaoImpl.class);
	
	@Override
	public PromotionCampaignShare findByCampaignNumber(
			String campaignNumber) {
		return this.template.selectOne(
				"findPromotionCampaignShareByCampaignNumber", campaignNumber);
	}

	@Override
	public void updateShare(PromotionCampaignShare shareInfo) {
		try{
			this.template.update("updatePromotionCampaignShareInfo", shareInfo);
		}catch(Exception e){
			LOGGER.error("更新分享信息出错,活动编号:{}", shareInfo.getCampaignNumber());
		}
	}
	
}
