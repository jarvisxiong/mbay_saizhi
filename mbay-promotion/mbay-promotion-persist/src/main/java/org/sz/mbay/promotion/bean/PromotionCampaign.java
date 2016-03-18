package org.sz.mbay.promotion.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.CampaignStep;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.promotion.enums.Model;
import org.sz.mbay.promotion.enums.LimitType;

/**
 * @Description: 促销神器实体bean
 * @author frank.zong
 * @date 2015-1-5 上午10:37:26
 * 		
 */
@SuppressWarnings("serial")
public class PromotionCampaign implements Serializable {
	
	/** 用户标识 ***/
	private String usernumber;
	
	/** 活动编号唯一标识 */
	private String eventnumber;
	
	/** 活动名称 */
	@NotBlank
	@Size(min = 1, max = 40)
	private String eventname;
	
	/** 创建时间 **/
	private DateTime createtime;
	
	/** 活动开始日期 ***/
	private DateTime starttime;
	
	/** 活动结束日期 ***/
	private DateTime endingtime;
	
	/** 有效期 ***/
	private int validityday;
	
	/** 已送出数量 **/
	private int sendednum;
	
	/** 已发放数量 **/
	private int gotnum;
	
	/** 活动状态 ***/
	private CampaignStatus state;
	
	/** 流量产品 **/
	private List<PromotionCampaignPackage> packages;
	
	/** 美贝产品 **/
	private List<PromotionCampaignMbay> mbays;
	
	/** 当前活动完整情况，不做实际用途，仅用于记录活动创建步骤，以便继续 */
	private CampaignStep step;
	
	/** 超出是否可继续参与活动 */
	private boolean continuable;
	
	/** 发送短信通知 */
	private boolean sendsms;
	
	/** 是否开启核销码 */
	private boolean verificate;
	
	/** 活动限制值 */
	private Integer campaignLimitValue;
	
	/** 活动限制类型 */
	private LimitType campaignLimitType;
	
	/** 手机号限制值 */
	private Integer mobileLimitValue;
	
	/** 手机号限制类型 */
	private LimitType mobileLimitType;
	
	/** 中奖概率 */
	private Double trafficRate;
	
	/** 查询标题 */
	private String queryTitle;
	
	/** 查询详情 */
	private String queryContent;
	
	/** 告罄提醒手机号 */
	private String thresholdMobile;
	
	/** 所属商城 */
	private DuiBaMall mall;
	
	/** 是否分享 */
	private boolean share;
	
	/** 选择模式 */
	private Model model;
	
	/** 赠送MB */
	private Integer sendMB;
	
	public Model getModel() {
		return model;
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	
	public Integer getSendMB() {
		return sendMB;
	}
	
	public void setSendMB(Integer sendMB) {
		this.sendMB = sendMB;
	}
	
	public boolean isShare() {
		return share;
	}
	
	public void setShare(boolean share) {
		this.share = share;
	}
	
	public DuiBaMall getMall() {
		return mall;
	}
	
	public void setMall(DuiBaMall mall) {
		this.mall = mall;
	}
	
	public String getThresholdMobile() {
		return thresholdMobile;
	}
	
	public void setThresholdMobile(String thresholdMobile) {
		this.thresholdMobile = thresholdMobile;
	}
	
	public String getQueryTitle() {
		return queryTitle;
	}
	
	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}
	
	public String getQueryContent() {
		return queryContent;
	}
	
	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}
	
	public Double getTrafficRate() {
		return trafficRate;
	}
	
	public void setTrafficRate(Double trafficRate) {
		this.trafficRate = trafficRate;
	}
	
	public List<PromotionCampaignPackage> getPackages() {
		return packages;
	}
	
	public void setPackages(List<PromotionCampaignPackage> packages) {
		this.packages = packages;
	}
	
	public List<PromotionCampaignMbay> getMbays() {
		return mbays;
	}
	
	public void setMbays(List<PromotionCampaignMbay> mbays) {
		this.mbays = mbays;
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
	
	public int getValidityday() {
		return validityday;
	}
	
	public void setValidityday(int validityday) {
		this.validityday = validityday;
	}
	
	public int getSendednum() {
		return sendednum;
	}
	
	public void setSendednum(int sendednum) {
		this.sendednum = sendednum;
	}
	
	public int getGotnum() {
		return gotnum;
	}
	
	public void setGotnum(int gotnum) {
		this.gotnum = gotnum;
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
	
	public boolean isSendsms() {
		return sendsms;
	}
	
	public void setSendsms(boolean sendsms) {
		this.sendsms = sendsms;
	}
	
	public boolean isVerificate() {
		return verificate;
	}
	
	public void setVerificate(boolean verificate) {
		this.verificate = verificate;
	}
	
	public Integer getCampaignLimitValue() {
		return campaignLimitValue;
	}
	
	public void setCampaignLimitValue(Integer campaignLimitValue) {
		this.campaignLimitValue = campaignLimitValue;
	}
	
	public LimitType getCampaignLimitType() {
		return campaignLimitType;
	}
	
	public void setCampaignLimitType(LimitType campaignLimitType) {
		this.campaignLimitType = campaignLimitType;
	}
	
	public Integer getMobileLimitValue() {
		return mobileLimitValue;
	}
	
	public void setMobileLimitValue(Integer mobileLimitValue) {
		this.mobileLimitValue = mobileLimitValue;
	}
	
	public LimitType getMobileLimitType() {
		return mobileLimitType;
	}
	
	public void setMobileLimitType(LimitType mobileLimitType) {
		this.mobileLimitType = mobileLimitType;
	}
	
}
