package org.sz.mbay.sms.template.enums;

import org.sz.mbay.sms.template.base.BaseSMS;

/**
 * @Description: 短信类型
 * @author han.han
 * @date 2014-11-10 下午10:51:49
 * 		
 */
public enum CaptchaSMSType implements BaseSMS {
	
	CERTIFICATE("实名认证短信"),
	
	RESET_PASSWORD("短信找回密码"),
	
	WL_REGISTER_CODE("美贝钱包注册获取验证码"),
	
	WL_MODIFY_PASSWD("美贝钱包修改密码获取验证码"),
	
	CAPTCHA_COMMON("验证码通用模板"),
	
	PHILIPS("飞利浦");
	
	public String value;
	
	private CaptchaSMSType(String value) {
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
