package org.sz.mbay.wechat.config;

import org.sz.mbay.base.config.ResourceConfig;
import org.sz.mbay.wechat.contant.WeChatCampaign;

public class WeChatResourceConfig extends ResourceConfig {
	
	/**
	 * 微信伴侣域名
	 */
	private static String WECHAT_DOMAIN = "wechatDomain";
	
	/**
	 * 获取微信伴侣域名
	 */
	public static String getWeChatDomain() {
		return ResourceConfig.getProperty(WECHAT_DOMAIN);
	}
	
	/**
	 * 获取开发者模式地址
	 */
	public static String getCampaignAdvancedURL() {
		return getWeChatDomain() + getContextPath() + WeChatCampaign.CAMPAIGN_ADVANCED_URL;
	}

	/**
	 * 获取编辑模式地址
	 */
	public static String getCampaignTemplateURL() {
		return getWeChatDomain() + getContextPath() + WeChatCampaign.CAMPAIGN_TEMPLATE_URL;
	}
}
