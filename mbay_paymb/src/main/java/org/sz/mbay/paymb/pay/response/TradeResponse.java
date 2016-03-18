package org.sz.mbay.paymb.pay.response;

import org.codehaus.jackson.annotate.JsonIgnore;

public final  class TradeResponse {

	private final String error_code;

	private final  String message;

	// 私有化构造器。以便使返回信息全部在此定义

	private TradeResponse(final String code, final String message) {
		this.error_code = code;
		this.message = message;
	}

	public static TradeResponse mbTradeFail(final String failReson) {
		return new TradeResponse("MB_TRADE_FAIL", failReson);
	}

	public static TradeResponse PAY_SUCCESS = new TradeResponse("PAY_SUCCE SS", "交易成功");

	public static TradeResponse PARAMETER_ERROR = new TradeResponse("PARAMETER_ERROR", "参数缺失或参数格式不正确！");

	public static TradeResponse ILLEGAL_PARTNER = new TradeResponse("ILLEGAL_PARTNER", "合作伙伴ID不正确");

	public static TradeResponse ILLEGAL_SIGN = new TradeResponse("ILLEGAL_SIGN", "签名不正确！");
	
	public static TradeResponse MB_COONCT_ERROR = new TradeResponse("MB_COONCT_ERROR", "MB接口连接异常");

	public static final class Pay {
		public static TradeResponse MB_TRADE_FAIL = new TradeResponse("MB_TRADE_FAIL", "MB交易异常");

		public static TradeResponse SELLER_TRADE_FAIL = new TradeResponse("SELLER_TRADE_FAIL", "商家MB入账交易异常");

	}

	public static final class BalanceQuery {
		
		public static TradeResponse querySuccess(final double balance) {
			return new TradeResponse("QUERY_SUCCESS", String.valueOf(balance));
		}

	}
	
	public static final class Redeem{
		public static TradeResponse SELLER_TRADE_FAIL = new TradeResponse("SELLER_TRADE_FAIL", "商家MB出帐交易异常");
	}

	public String getError_code() {
		return error_code;
	}


	// 描述信息不展示给商户
	@JsonIgnore
	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		return "error_code：" + this.getError_code() + ";message=" + this.getMessage();
	}

}
