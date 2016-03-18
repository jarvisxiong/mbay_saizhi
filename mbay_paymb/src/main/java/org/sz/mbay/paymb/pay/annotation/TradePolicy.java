package org.sz.mbay.paymb.pay.annotation;

/**
*
* @author  han.han
*/
public enum TradePolicy {

	/**
	 * MB 支出
	 * */
	PAY("pay"),

	/**
	 * MB 余额查询
	 * */
	BALANCE("balance"),

	/**
	 * MB反兑
	 * */
	REDEEM("redeem");
	
	
	private final String policy;

	TradePolicy() {
		this.policy = null;
	}

	TradePolicy(final String policy) {
		this.policy = policy;
	}

	public String policy() {
		return this.policy;
	}

}
