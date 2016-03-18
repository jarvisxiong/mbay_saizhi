package org.sz.mbay.traffic.component.sms;

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
public class SMSUnit {
    private int smsId;
    private String param;

    public SMSUnit(int smsId, String param) {
	super();
	this.smsId = smsId;
	this.param = param;
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

}
