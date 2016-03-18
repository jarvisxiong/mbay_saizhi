package org.sz.mbay.channel.bean;

import org.sz.mbay.channel.enums.StoreCampaginTradeType;

/**
 * @Description: o2o活动订单
 * @author fenlon
 * 
 */
public class StoreCampaignOrder extends Order {
	
	private static final long serialVersionUID = -5930548506384276908L;
	/** 兑换码 **/
	private String redeemCode;
	/** 交易类型 **/
	private StoreCampaginTradeType type;
	
	public StoreCampaignOrder() {
	}
	
	public StoreCampaginTradeType getType() {
		return type;
	}
	
	public void setType(StoreCampaginTradeType type) {
		this.type = type;
	}
	
	public String getRedeemCode() {
		return redeemCode;
	}
	
	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}
}
