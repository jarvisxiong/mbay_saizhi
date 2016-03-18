package org.sz.mbay.promotion.config;

import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.promotion.contant.PromotionCampaign;

public class PromotionResourceConfig extends ResourceConfig {
	
	/**
	 * 促销神器域名
	 */
	private static String PROMOTION_DOMAIN = "promotionDomain";
	
	/**
	 * 获取促销神器域名
	 */
	public static String getPromotionDomain() {
		return ResourceConfig.getProperty(PROMOTION_DOMAIN);
	}
	
	/**
	 * 获取促销神器领取页面地址
	 */
	public static String getPromotionGetURL() {
		return getPromotionDomain() + getContextPath() + PromotionCampaign.PROMOTION_GET_URL;
	}

	/**
	 * 获取促销神器兑换页面地址
	 */
	public static String getPromotionRedeemURL() {
		return getPromotionDomain() + getContextPath() + PromotionCampaign.PROMOTION_REDEEM_URL;
	}
}
