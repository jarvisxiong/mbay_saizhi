package org.sz.mbay.promotion.contant;

/**
 * @Description: 促销神器常量
 * @author frank.zong
 * 
 */
public final class PromotionCampaign {
	
	/**兑换码路径**/
	public static final String REDEEM_CODE_URL = "/api/redeemcode/get.mbay";

	/**领取页面地址**/
	public static final String PROMOTION_GET_URL="/promotionCampaign/redeem_code/{campaignNumber}/get.mbay";
	
	/**兑换页面地址**/
	public static final String PROMOTION_REDEEM_URL="/promotionCampaign/redeem_code/{campaignNumber}/redeem.mbay";
}
