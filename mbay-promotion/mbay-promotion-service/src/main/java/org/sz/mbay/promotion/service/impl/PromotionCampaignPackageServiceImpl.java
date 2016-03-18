package org.sz.mbay.promotion.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sz.mbay.base.service.impl.BaseServiceImpl;
import org.sz.mbay.promotion.bean.PromotionCampaignPackage;
import org.sz.mbay.promotion.dao.PromotionCampaignPackageDao;
import org.sz.mbay.promotion.service.PromotionCampaignPackageService;

@Service
public class PromotionCampaignPackageServiceImpl extends BaseServiceImpl
		implements PromotionCampaignPackageService {
		
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PromotionCampaignPackageServiceImpl.class);

	@Autowired
	PromotionCampaignPackageDao dao;
	
	@Override
	public List<PromotionCampaignPackage> findByCampaignNumber(
			String campaignNumber) {
		return dao.findByCampaignNumber(campaignNumber);
	}
			
}
