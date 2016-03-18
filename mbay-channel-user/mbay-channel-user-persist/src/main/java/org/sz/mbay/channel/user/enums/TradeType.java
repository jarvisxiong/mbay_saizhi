package org.sz.mbay.channel.user.enums;

/**
 * @Description:
 * @author han.han
 * @date 2014-9-18 下午11:22:37
 * 
 */
public enum TradeType {
	
	NON("", ""),
	TRANSFER("转账", "00001"),
	TRAFFIC_RECHARGE("流量直充", "00002"),
	MBAY_DEPOSIT("美贝充值", "00003"),
	SMS_PURCHASE("短信购买", "00004"),
	SEND_MBAY("赠送美贝", "00005"),
	MBAY_APPROPR("财务拨款", "00006"),
	SMS_SEND("短信发送", "00007"),
	REDEEM_CODE_SEND("兑换码发送", "00008"),
	STORE_CAMPAGIN("门店活动", "00009"),
	TRAFFIC_REFUND("流量充值退款", "00010"),
	TRAFFIC_RED("流量红包", "00011"),
	MBAY_TRANSFER_IN_RED("转账", "00012"),
	WECHAT_CAMPAIGN("微信伴侣", "00013"),
	PROMOTION_CAMPAIGN("促销神器","00014"),
	CUSTOMER_SERVER("客户关怀","00015"),
	APP_CAMPAIGN("APP诱惑","00016"),
	MALL_REDEEM("商城兑换","00017");
	
	private String value;	
	// 编号
	private String number;
	
	private TradeType(String value, String number) {
		this.value = value;
		this.number = number;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
}
