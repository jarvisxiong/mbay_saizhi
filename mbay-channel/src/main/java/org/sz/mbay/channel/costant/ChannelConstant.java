package org.sz.mbay.channel.costant;

import org.sz.mbay.channel.context.ChannelConfig;

public class ChannelConstant {
	
	public static class Wallet {
		
		/** 钱包项目路径 */
		public static final String WALLET_URL = ChannelConfig
				.getWalletInterface();
				
		/** 钱包主页 */
		public static final String INDEX = WALLET_URL
				+ "/web/main/index/ui.mbay";
				
		/** 钱包活动页 */
		public static final String CAMPAIGN_LIST = WALLET_URL
				+ "/web/campaign/list/ui.mbay";
				
		/** 游戏 */
		public static final String YOUXI = WALLET_URL
				+ "/web/partner/izhangxin.mbay?redirectAfterLogin=true";
				
		/** 兑吧 */
		public static final String WL_DUIBA = WALLET_URL
				+ "/web/main/duiba/ui.mbay?redirectAfterLogin=true";
	}
}
