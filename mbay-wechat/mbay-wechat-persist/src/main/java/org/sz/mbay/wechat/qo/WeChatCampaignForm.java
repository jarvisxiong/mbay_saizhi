package org.sz.mbay.wechat.qo;

import java.io.Serializable;

import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.qo.BaseForm;

/**
* @Description: 活动列表查询
* @author frank.zong
* @date 2015-1-5 上午11:27:01 
*
 */
@SuppressWarnings("serial")
public class WeChatCampaignForm extends BaseForm implements Serializable {
	
	/** 用户编号 ***/
	private String usernumber;
	/** 活动编号 **/
	private String eventnumber;
	/** 活动状态 **/
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