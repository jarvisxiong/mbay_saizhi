package org.sz.mbay.captcha;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * @Description: 短信验证码包装类
 * @author han.han
 */
public class AuthCode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6437076037252262364L;
	
	// 验证码
	private String code;
	
	// 生成时间
	private DateTime generatedTime;
	
	// 已发送
	private boolean sended;
	
	// 验证错误次数
	private int errorTimes;
	
	// 验证码发送的手机号
	private String mobile;
	
	public AuthCode(String mobile, String code, DateTime generatedTime,
			int errorTimes) {
		this.mobile = mobile;
		this.code = code;
		this.generatedTime = generatedTime;
		this.errorTimes = errorTimes;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public DateTime getGeneratedTime() {
		return generatedTime;
	}
	
	public void setGeneratedTime(DateTime generatedTime) {
		this.generatedTime = generatedTime;
	}
	
	public int getErrorTimes() {
		return errorTimes;
	}
	
	public void setErrorTimes(int errorTimes) {
		this.errorTimes = errorTimes;
	}
	
	public boolean isSended() {
		return sended;
	}
	
	public void setSended(boolean sended) {
		this.sended = sended;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
