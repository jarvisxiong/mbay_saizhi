package org.sz.mbay.apptemptation.form;

import java.io.Serializable;

import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.qo.BaseForm;

/**
 * 活动列表查询
 * 
 * @author jerry
 */
@SuppressWarnings("serial")
public class AppTemptationForm extends BaseForm implements Serializable {
	
	// 用户编号
	private String usernumber;
	
	// 活动编号
	private String eventnumber;
	
	// 活动状态
	private CampaignStatus eventstate;
	
	public String getUsernumber() {
		return usernumber;
	}
	
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	
	public String getEventnumber() {
		return eventnumber;
	}
	
	public void setEventnumber(String eventnumber) {
		this.eventnumber = eventnumber;
	}
	
	public CampaignStatus getEventstate() {
		return eventstate;
	}
	
	public void setEventstate(CampaignStatus eventstate) {
		this.eventstate = eventstate;
	}
}
