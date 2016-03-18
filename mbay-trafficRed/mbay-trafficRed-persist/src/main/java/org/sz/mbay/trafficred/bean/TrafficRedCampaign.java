package org.sz.mbay.trafficred.bean;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;
import org.sz.mbay.base.annotation.valid.Mobile;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.duiba.bean.DuiBaMall;
import org.sz.mbay.trafficred.enums.SharkCycleType;
import org.sz.mbay.trafficred.valid.group.CampaignCreate;
import org.sz.mbay.trafficred.valid.group.CampaignUpdate;

/**
 * 流量红包活动实体
 * 
 * @author Fenlon
 */
public class TrafficRedCampaign extends BaseEntityModel {
	
	private static final long serialVersionUID = -7623609364284065395L;
	
	// 活动名称
	@Size(max = 40, groups = { CampaignCreate.class })
	@NotNull(groups = { CampaignCreate.class })
	private String name;
	
	// 是否先摇一摇
	@NotNull(groups = { CampaignCreate.class })
	private Boolean firstShark;
	
	// 活动编号
	private String number;
	
	// 商户编号
	private String userNumber;
	
	// 活动状态
	private CampaignStatus status;
	
	// 是否发送短息提醒
	@NotNull(groups = { CampaignCreate.class })
	private Boolean sendsms;
	
	// 活动开始日期
	private DateTime startTime;
	
	// 活动结束日期
	private DateTime endTime;
	
	// 活动开始时间段
	private List<TimeQuantum> timeQuantums;
	
	// 活动支持的每家运营商的流量包
	private List<TrafficRedCampaignPackage> packages;
	
	// 活动美贝包
	private List<TrafficRedCampaignMbay> mbays;
	
	// 流量中奖率
	@Digits(integer = 3, fraction = 5,
			groups = { CampaignCreate.class, CampaignUpdate.class })
	@NotNull(groups = { CampaignCreate.class, CampaignUpdate.class })
	private Double trafficRate;
	
	// 次数
	@DecimalMin(value = "0",
			groups = { CampaignCreate.class, CampaignUpdate.class })
	@DecimalMax(value = "9999999999",
			groups = { CampaignCreate.class, CampaignUpdate.class })
	private Integer times;
	
	// 摇一摇周期策略
	@NotNull(groups = { CampaignCreate.class, CampaignUpdate.class })
	private SharkCycleType type;
	
	// 活动模板
	@Valid
	private TrafficRedTemplate template;
	
	// 活动分享信息
	@Valid
	private TrafficRedShareInfo shareInfo;
	
	// 送人分享信息
	@Valid
	private TrafficRedMbayGiftConfig mbayGiftConfig;
	
	// 商城
	@NotNull(groups = { CampaignCreate.class, CampaignUpdate.class })
	private DuiBaMall mall;
	
	// 创建日子
	private DateTime dateCreated;
	
	// 阀值提醒号码
	@Mobile(groups = { CampaignCreate.class, CampaignUpdate.class })
	@NotNull(groups = { CampaignCreate.class, CampaignUpdate.class })
	private String thresholdMobile;
	
	public DateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(DateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getFirstShark() {
		return firstShark;
	}
	
	public void setFirstShark(Boolean firstShark) {
		this.firstShark = firstShark;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public CampaignStatus getStatus() {
		return status;
	}
	
	public void setStatus(CampaignStatus status) {
		this.status = status;
	}
	
	public Boolean getSendsms() {
		return sendsms;
	}
	
	public void setSendsms(Boolean sendsms) {
		if (sendsms == null) {
			sendsms = false;
		} else {
			this.sendsms = sendsms;
		}
	}
	
	public DateTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	
	public DateTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
	
	public List<TimeQuantum> getTimeQuantums() {
		return timeQuantums;
	}
	
	public void setTimeQuantums(List<TimeQuantum> timeQuantums) {
		this.timeQuantums = timeQuantums;
	}
	
	public List<TrafficRedCampaignMbay> getMbays() {
		return mbays;
	}
	
	public void setMbays(List<TrafficRedCampaignMbay> mbays) {
		this.mbays = mbays;
	}
	
	public Double getTrafficRate() {
		return trafficRate;
	}
	
	public void setTrafficRate(Double trafficRate) {
		this.trafficRate = trafficRate;
	}
	
	public Integer getTimes() {
		return times;
	}
	
	public void setTimes(Integer times) {
		this.times = times;
	}
	
	public SharkCycleType getType() {
		return type;
	}
	
	public void setType(SharkCycleType type) {
		this.type = type;
	}
	
	public TrafficRedTemplate getTemplate() {
		return template;
	}
	
	public void setTemplate(TrafficRedTemplate template) {
		this.template = template;
	}
	
	public List<TrafficRedCampaignPackage> getPackages() {
		return packages;
	}
	
	public void setPackages(List<TrafficRedCampaignPackage> packages) {
		this.packages = packages;
	}
	
	public DuiBaMall getMall() {
		return mall;
	}
	
	public void setMall(DuiBaMall mall) {
		this.mall = mall;
	}
	
	public TrafficRedShareInfo getShareInfo() {
		return shareInfo;
	}
	
	public void setShareInfo(TrafficRedShareInfo shareInfo) {
		this.shareInfo = shareInfo;
	}
	
	public String getThresholdMobile() {
		return thresholdMobile;
	}
	
	public void setThresholdMobile(String thresholdMobile) {
		this.thresholdMobile = thresholdMobile;
	}
	
	public TrafficRedMbayGiftConfig getMbayGiftConfig() {
		return mbayGiftConfig;
	}
	
	public void setMbayGiftConfig(TrafficRedMbayGiftConfig mbayGiftConfig) {
		this.mbayGiftConfig = mbayGiftConfig;
	}
	
}
