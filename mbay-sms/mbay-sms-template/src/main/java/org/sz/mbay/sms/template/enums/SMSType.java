package org.sz.mbay.sms.template.enums;

import org.sz.mbay.sms.template.base.BaseSMS;

/**
 * @Description: 短信类型
 * @author han.han
 * @date 2014-11-10 下午10:51:49
 * 		
 */
public enum SMSType implements BaseSMS {
	
	NON(""),
	
	ACTIVITY("微信营销流量下发通知"),
	
	REDEEM_CODE_GRANT("兑换码发放短信"),
	
	SMS_NUM_REMIND("短信剩余不足提醒"),
	
	MBAY_BALANCE_REMIND("美贝余额不足提醒"),
	
	CERTIFICATE_SUCCESS("实名认证通过提醒"),
	
	CUSTOMER_SERVER("客户关怀短信"),
	
	LIUMI_REMIND("流米通知"),
	
	@Deprecated BJ_PREASSEMBLE_CODE("北京电信活动，获取预装码"),
	
	WL_TRAFFIC_EXCHANGE("美贝钱包流量兑换"),
	
	TR_THRESHOLD_WARNING("流量红包阀值警告");
	
	public String value;
	
	private SMSType(String value) {
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getName() {
		return name();
	}
}
