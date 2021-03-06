package org.sz.mbay.wechat.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.base.enums.SendWay;

/**
 * @Description: 微信伴侣实体bean
 * @author frank.zong
 * @date 2015-1-5 上午10:37:26
 * 
 */
public class WeChatCampaign implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5501817206412857984L;

	/** 用户标识 ***/
	private String usernumber;

	/** 活动编号唯一标识 */
	private String eventnumber;

	/** 活动名称 */
	@NotBlank
	@Size(min=1,max=40)
	private String eventname;

	/** 创建时间 **/
	private DateTime createtime;

	/** 活动开始日期 ***/
	private DateTime starttime;

	/** 活动结束日期 ***/
	private DateTime endingtime;

	/** 赠送方式 ***/
	private SendWay sendway;

	/** 单个用户赠送美贝数量 ***/
	private int mbaysendunit;

	/** 预计总量 ***/
	@Min(value=1)
	private int quantity;

	/** 已送出数量 **/
	private int sendednum;

	/** 总额度 ****/
	private double amount;

	/** 送出的美贝 ***/
	private double costamount;

	/** 活动状态 ***/
	private CampaignStatus state;

	/** 活动策略 **/
	private List<WeChatCampaignStrategy> stratetgylist;

	/** 当前活动完整情况，不做实际用途，仅用于记录活动创建步骤，以便继续 */
	private CampaignStep step;

	/** 单个手机号是否可重复领取 */
	private boolean repeatEnable;

	/** 超出预计总量是否可继续参与活动 */
	private boolean continuable;
	
	/** 是否可以直接领取 */
	private boolean directEnable;
	
	/** 发送短信通知 */
	private boolean sendsms;
	
	public boolean isRepeatEnable() {
		return repeatEnable;
	}

	public void setRepeatEnable(boolean repeatEnable) {
		this.repeatEnable = repeatEnable;
	}

	public List<WeChatCampaignStrategy> getStratetgylist() {
		return stratetgylist;
	}

	public void setStratetgylist(List<WeChatCampaignStrategy> stratetgylist) {
		this.stratetgylist = stratetgylist;
	}

	public int getMbaysendunit() {
		return mbaysendunit;
	}

	public void setMbaysendunit(int mbaysendunit) {
		this.mbaysendunit = mbaysendunit;
	}

	public String getEventnumber() {
		return eventnumber;
	}

	public void setEventnumber(String eventnumber) {
		this.eventnumber = eventnumber;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public DateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(DateTime createtime) {
		this.createtime = createtime;
	}

	public DateTime getStarttime() {
		return starttime;
	}

	public void setStarttime(DateTime starttime) {
		this.starttime = starttime;
	}

	public DateTime getEndingtime() {
		return endingtime;
	}

	public void setEndingtime(DateTime endingtime) {
		this.endingtime = endingtime;
	}

	public SendWay getSendway() {
		return sendway;
	}

	public void setSendway(SendWay sendway) {
		this.sendway = sendway;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSendednum() {
		return sendednum;
	}

	public void setSendednum(int sendednum) {
		this.sendednum = sendednum;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getCostamount() {
		return costamount;
	}

	public void setCostamount(double costamount) {
		this.costamount = costamount;
	}

	public CampaignStatus getState() {
		return state;
	}

	public void setState(CampaignStatus state) {
		this.state = state;
	}

	public String getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}

	public CampaignStep getStep() {
		return step;
	}

	public void setStep(CampaignStep step) {
		this.step = step;
	}

	public boolean isContinuable() {
		return continuable;
	}

	public void setContinuable(boolean continuable) {
		this.continuable = continuable;
	}
	
	public boolean isDirectEnable() {
		return directEnable;
	}
	
	public void setDirectEnable(boolean directEnable) {
		this.directEnable = directEnable;
	}

	public boolean isSendsms() {
		return sendsms;
	}

	public void setSendsms(boolean sendsms) {
		this.sendsms = sendsms;
	}
}