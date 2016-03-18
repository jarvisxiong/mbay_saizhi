package org.sz.mbay.sms.bean;

/**
 * 
 * @ClassName: SMSUnit
 * 
 * @Description: smsTemplateSupport 根据用户活动类型得到短信模板对象，然后封装此类
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date 2014年12月18日 下午12:39:50
 * 
 */
public class SmsUnit {
	
	/** 短信模板ID **/
	private int smsId;
	/** 占位符替换内容，用‘,’隔开 **/
	private String param;
	/** 短信模板内容 **/
	private String content;
	
	public SmsUnit() {}
	
	public SmsUnit(int smsId, String param, String content) {
		this.smsId = smsId;
		this.param = param;
		this.content = content;
	}
	
	public int getSmsId() {
		return smsId;
	}
	
	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}
	
	public String getParam() {
		return param;
	}
	
	public void setParam(String param) {
		this.param = param;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
