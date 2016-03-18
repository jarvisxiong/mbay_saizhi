package org.sz.mbay.channel.bean;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 门店对应活动记录
 * 
 * @author Fenlon
 * 
 */
public class StoreCampaignRecord extends BaseEntityModel {
	
	private static final long serialVersionUID = -4300437304109342756L;
	/** 门店 **/
	@JsonIgnore
	private Store store;
	/** 门店活动 **/
	private StoreCampaign storeCampaign;
	/** 发放数目 **/
	private int deliverNum;
	/** 兑换数目 **/
	private int redeemNum;
	/** 是否退出活动 **/
	private boolean exited;
	
	public Store getStore() {
		return store;
	}
	
	public void setStore(Store store) {
		this.store = store;
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
	
	public boolean isExited() {
		return exited;
	}
	
	public void setExited(boolean exited) {
		this.exited = exited;
	}
	
	public StoreCampaign getStoreCampaign() {
		return storeCampaign;
	}
	
	public void setStoreCampaign(StoreCampaign storeCampaign) {
		this.storeCampaign = storeCampaign;
	}
}
