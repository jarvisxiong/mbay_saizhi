package org.sz.mbay.promotion.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.promotion.bean.PromotionCampaignMbay;
import org.sz.mbay.promotion.dao.PromotionCampaignMbayDao;
import org.sz.mbay.promotion.service.PromotionCampaignMbayService;

@Service
public class PromotionCampaignMbayServiceImpl extends BaseServiceImpl
		implements PromotionCampaignMbayService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionCampaignMbayServiceImpl.class);
			
	@Autowired
	PromotionCampaignMbayDao dao;
	
	@Override
	public List<PromotionCampaignMbay> findByCampaignNumber(
			String campaignNumber) {
		return dao.findByCampaignNumber(campaignNumber);
	}
}
