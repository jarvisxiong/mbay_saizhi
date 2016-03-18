package org.sz.mbay.channel.bean;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;
import org.sz.mbay.base.enums.CampaignStatus;
import org.sz.mbay.base.enums.EnableState;

/**
 * 活动
 * 
 * @author Fenlon
 * 
 */
public class StoreCampaign extends BaseEntityModel {
	
	private static final long serialVersionUID = 650466475929684554L;
	
	/** 活动编号唯一标识 */
	private String number;
	
	/** 活动名称 */
	private String name;
	
	/** 用户标识 ***/
	private String userNumber;
	
	/** 活动开始日期 ***/
	private DateTime startTime;
	
	/** 活动结束日期 ***/
	private DateTime endTime;
	
	/** 兑换码有效期 **/
	private int validity;
	/** 活动状态 **/
	private CampaignStatus status;
	/** 启用禁用 **/
	private EnableState state;
	/** 预计总量 ***/
	private int quantity;
	/** 活动发放价值 ***/
	private float price;
	/** 发放总量 ***/
	private int deliverNum;
	/** 兑换计总量 ***/
	private int redeemNum;
	
	/** 单个手机号是否可重复领取 */
	private boolean repeatGet;
	
	/** 是否及时获取 */
	private boolean getInTime;
	
	/** 发出一个红包扣除相应的美贝 **/
	private double deductSend = 1;
	
	/** 兑换一个红包扣除相应的美贝 **/
	private double deductReedem = 1;
	
	/** 活动链接 **/
	private String link;
	
	/*** logo对应的id */
	private long logoId;
	
	/** 美贝直通车平台发放红包最大数 **/
	private int mbayPlatSend;
	
	/** 活动锁定美贝 **/
	private double lockMbay;
	/** 活动消耗美贝 **/
	private double costMbay;
	/** 活动说明 **/
	private String describtion;
	
	public StoreCampaign() {
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public int getValidity() {
		return validity;
	}
	
	public void setValidity(int validity) {
		this.validity = validity;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public boolean isRepeatGet() {
		return repeatGet;
	}
	
	public void setRepeatGet(boolean repeatGet) {
		this.repeatGet = repeatGet;
	}
	
	public boolean isGetInTime() {
		return getInTime;
	}
	
	public void setGetInTime(boolean getInTime) {
		this.getInTime = getInTime;
	}
	
	public double getDeductSend() {
		return deductSend;
	}
	
	public void setDeductSend(double deductSend) {
		this.deductSend = deductSend;
	}
	
	public double getDeductReedem() {
		return deductReedem;
	}
	
	public void setDeductReedem(double deductReedem) {
		this.deductReedem = deductReedem;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public long getLogoId() {
		return logoId;
	}
	
	public void setLogoId(long logoId) {
		this.logoId = logoId;
	}
	
	public int getMbayPlatSend() {
		return mbayPlatSend;
	}
	
	public void setMbayPlatSend(int mbayPlatSend) {
		this.mbayPlatSend = mbayPlatSend;
	}
	
	public double getLockMbay() {
		return lockMbay;
	}
	
	public void setLockMbay(double lockMbay) {
		this.lockMbay = lockMbay;
	}
	
	public double getCostMbay() {
		return costMbay;
	}
	
	public void setCostMbay(double costMbay) {
		this.costMbay = costMbay;
	}
	
	public String getDescribtion() {
		return describtion;
	}
	
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	
	public CampaignStatus getStatus() {
		return status;
	}
	
	public void setStatus(CampaignStatus status) {
		this.status = status;
	}
	
	public int getDeliverNum() {
		return deliverNum;
	}
	
	public void setDeliverNum(int deliverNum) {
		this.deliverNum = deliverNum;
	}
	
	public int getRedeemNum() {
		return redeemNum;
	}
	
	public void setRedeemNum(int redeemNum) {
		this.redeemNum = redeemNum;
	}
	
	public EnableState getState() {
		return state;
	}
	
	public void setState(EnableState state) {
		this.state = state;
	}
	
	@Override
	public int hashCode() {
		if (this.number == null) {
			this.number = "";
		}
		return HashCodeBuilder.reflectionHashCode(this.number);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null != obj) {
			if (obj instanceof StoreCampaign) {
				StoreCampaign domain = (StoreCampaign) obj;
				if (this.number.equals(domain.number)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
}
