package org.sz.mbay.promotion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.promotion.bean.PromotionCampaignShare;
import org.sz.mbay.promotion.dao.PromotionCampaignShareDao;
import org.sz.mbay.promotion.service.PromotionCampaignShareService;

@Service
public class PromotionCampaignShareServiceImpl extends BaseServiceImpl
		implements PromotionCampaignShareService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionCampaignShareServiceImpl.class);
			
	@Autowired
	PromotionCampaignShareDao dao;
	
	@Override
	public PromotionCampaignShare findByCampaignNumber(
			String campaignNumber) {
		return dao.findByCampaignNumber(campaignNumber);
	}
	
	@Override
	public void addShare(PromotionCampaignShare shareInfo) {
		try{
			dao.createBean(shareInfo);
		}catch(Exception e){
			LOGGER.error("添加分享信息出错,活动编号:{}", shareInfo.getCampaignNumber());
		}
	}
	
	@Override
	public void updateShare(PromotionCampaignShare shareInfo) {
		dao.updateShare(shareInfo);
	}
}
