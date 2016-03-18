package org.sz.mbay.channel.context;

import javax.servlet.http.HttpServlet;
import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.channel.costant.WeChatCampaign;
import org.sz.mbay.promotion.config.PromotionResourceConfig;
import org.sz.mbay.promotion.contant.PromotionCampaign;
import org.sz.mbay.wechat.config.WeChatResourceConfig;

/**
 * 配置文件类
 * 
 * @author frank.zong
 */
@SuppressWarnings("serial")
public class ChannelConfig extends HttpServlet {
	
	/**
	 * 请求兑换流量接口路径
	 */
	private static String TRAFFICMBYREDEEMURL = "trafficMbayRedeemURL";
	
	/**
	 * 请求支付接口路径
	 */
	private static String MBAY_PAY_URL = "mbayPayURL";
	
	/**
	 * 市场账号
	 */
	private static String MARKET_USERNUMBER = "market_usernumber";
	
	/**
	 * 商城id
	 */
	private static String MALLID = "mallId";
	
	/**
	 * 抽奖商城id
	 */
	private static String RAFFLE_MALLID = "raffle_mallId";
	
	/**
	 * 美贝钱包接口地址
	 */
	private static String WALLET_INTERFACE = "wallet_interface";
	
	/**
	 * 获取兑换码路径
	 */
	public static String getRedeemCodeURL() {
		return PromotionResourceConfig.getPromotionDomain()
				+ ResourceConfig.getContextPath()
				+ PromotionCampaign.REDEEM_CODE_URL;
	}
	
	/**
	 * 通过美贝兑换流量请求兑换接口路径
	 */
	public static String getTrafficMbayRedeemURL() {
		return ResourceConfig.getProperty(TRAFFICMBYREDEEMURL);
	}
	
	/**
	 * 获取微信伴侣开发者模式地址
	 */
	public static String getCampaignAdvancedURL() {
		return WeChatResourceConfig.getWeChatDomain()
				+ ResourceConfig.getContextPath()
				+ WeChatCampaign.CAMPAIGN_ADVANCED_URL;
	}
	
	/**
	 * 获取微信伴侣编辑模式地址
	 */
	public static String getCampaignTemplateURL() {
		return WeChatResourceConfig.getWeChatDomain()
				+ ResourceConfig.getContextPath()
				+ WeChatCampaign.CAMPAIGN_TEMPLATE_URL;
	}
	
	/**
	 * 获取促销神器领取页面地址
	 */
	public static String getPromotionGetURL() {
		return PromotionResourceConfig.getPromotionDomain()
				+ ResourceConfig.getContextPath()
				+ PromotionCampaign.PROMOTION_GET_URL;
	}
	
	/**
	 * 获取促销神器兑换页面地址
	 */
	public static String getPromotionRedeemURL() {
		return PromotionResourceConfig.getPromotionDomain()
				+ ResourceConfig.getContextPath()
				+ PromotionCampaign.PROMOTION_REDEEM_URL;
	}
	
	/**
	 * 获取美贝支付请求地址
	 */
	public static String getMbayPayURL() {
		return ResourceConfig.getProperty(MBAY_PAY_URL);
	}
	
	/** 获取市场账号 */
	public static String getMarketUsernumber() {
		return ResourceConfig.getProperty(MARKET_USERNUMBER);
	}
	
	/**
	 * 获取商城id
	 */
	public static String getMallId() {
		return ResourceConfig.getProperty(MALLID);
	}
	
	/**
	 * 获取抽奖商城id
	 */
	public static String getRaffleMallId() {
		return ResourceConfig.getProperty(RAFFLE_MALLID);
	}
	
	/**
	 * 美贝钱包接口地址
	 * 
	 * @return
	 */
	public static String getWalletInterface() {
		return ResourceConfig.getProperty(WALLET_INTERFACE);
	}
}
