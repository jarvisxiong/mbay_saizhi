package org.sz.mbay.base.constant;

/**
 * @Description: 流水号种子
 * @author han.han
 * @date 2014-10-30 下午12:46:59
 * 		
 */
public final class SerialSeed {
	
	/** 流水基数 **/
	public static final int CARDINAL_NUMBER = 100000;
	
	/**
	 * @Description: 活动流水号
	 * @author han.han
	 * @date 2014-10-30 下午12:47:18
	 * 		
	 */
	public static final class Event {
		
		// 微信营销流水号
		public static final String WECHAT = "110";
		// 促销神其流水号
		public static final String REDEEM = "120";
		// O2O其流水号
		public static final String O2O_ACTIVITY = "130";
		// app诱惑流水号
		public static final String APP_TEMPTATION = "140";
		// 流量红包流水号
		public static final String TRAFFIC_RED = "150";
		// 商城流水号
		public static final String MALL = "160";
	}
	
	/**
	 * @Description: 活动策略流水号
	 * @author han.han
	 * @date 2014-10-30 下午12:47:18
	 * 		
	 */
	public static final class EventStrategy {
		
		// 微信营销流水号
		public static final String WECHAT_STRATEGY = "111";
		// 促销神其流水号
		public static final String REDEEM_STRATEGY = "121";
		// app诱惑策略流水号
		public static final String APP_TEMPTATION_STRATEGY = "131";
	}
	
	// 收支明细流水号
	public static final String ACCOUNT_DETAIL = "210";
	// 短信购买
	public static final String SMS_PURCHASE = "310";
	// 短信发送订单
	public static final String SMS_SEND_ORDER = "410";
	
}
