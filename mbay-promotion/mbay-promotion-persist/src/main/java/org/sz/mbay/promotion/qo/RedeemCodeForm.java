package org.sz.mbay.promotion.qo;

import org.joda.time.DateTime;
import org.sz.mbay.base.qo.BaseForm;
import org.sz.mbay.promotion.enums.RedeemCodeStu;

/**
 * @Description: 兑换码发放查询提交Form
 * @author han.han
 * @date 2014-11-13 上午12:54:25
 * 		
 */
public class RedeemCodeForm extends BaseForm {
	
	// 活动编号
	private String eventNumber;
	// 兑换码状态
	private RedeemCodeStu codeStatus;
	// 兑换码
	private String code;
	// 手机号
	private String mobile;
	// 截至日期
	private String time;
	// 核销码
	private String verificateCode;
	// 领取开始时间
	private DateTime getStartTime;
	// 领取结束时间
	private DateTime getEndTime;
	// 兑换开始时间
	private DateTime redeemStartTime;
	// 兑换结束时间
	private DateTime redeemEndTime;
	
	public String getVerificateCode() {
		return verificateCode;
	}
	
	public void setVerificateCode(String verificateCode) {
		this.verificateCode = verificateCode;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getEventNumber() {
		return eventNumber;
	}
	
	public void setEventNumber(String eventNumber) {
		this.eventNumber = eventNumber;
	}
	
	public RedeemCodeStu getCodeStatus() {
		return codeStatus;
	}
	
	public void setCodeStatus(RedeemCodeStu codeStatus) {
		this.codeStatus = codeStatus;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public DateTime getGetStartTime() {
		return getStartTime;
	}
	
	public void setGetStartTime(DateTime getStartTime) {
		this.getStartTime = getStartTime;
	}
	
	public DateTime getGetEndTime() {
		return getEndTime;
	}
	
	public void setGetEndTime(DateTime getEndTime) {
		this.getEndTime = getEndTime;
	}
	
	public DateTime getRedeemStartTime() {
		return redeemStartTime;
	}
	
	public void setRedeemStartTime(DateTime redeemStartTime) {
		this.redeemStartTime = redeemStartTime;
	}
	
	public DateTime getRedeemEndTime() {
		return redeemEndTime;
	}
	
	public void setRedeemEndTime(DateTime redeemEndTime) {
		this.redeemEndTime = redeemEndTime;
	}
	
}
