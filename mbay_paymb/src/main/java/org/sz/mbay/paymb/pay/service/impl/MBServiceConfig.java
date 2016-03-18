package org.sz.mbay.paymb.pay.service.impl;

import org.sz.mbay.base.config.ResourceConfig;

/**
 * MB交易 接口配置信息    包级所有
 * 
 * @author han.han
 *
 */
final class MBServiceConfig {

	static class Balance {

		public static final String MODULE_CODE = "A0002"; // 交易编号

		public static final String URL = ResourceConfig.getProperty("mb_balance_url"); // 交易地址

	}

	static class Pay {

		public static final String MODULE_CODE = "A0004"; // 交易编号

		public static final String URL = ResourceConfig.getProperty("mb_expenditure_url");// 交易地址

		public static final String TRADE_TYPE = "MALL_REDEEM"; // 交易类型
	}

	static class Redeem {

		public static final String MODULE_CODE = "A0003"; // 交易编号

		public static final String URL = ResourceConfig.getProperty("mb_redeem_url"); // 交易地址

		public static final String TRADE_TYPE = "MALL_REDEEM"; // 交易类型
	}

	static class USignUp {
		public static final String MODULE_CODE = "A0001"; // 交易编号

		public static final String URL = ResourceConfig.getProperty("mb_usignup_url"); // 交易地址

		public static final String SIGN_SORUCE = "商家MB赠送";// 注册来源

	}

}
