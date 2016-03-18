package org.sz.mbay.channel.useraccount.bean;

import org.joda.time.DateTime;
import org.sz.mbay.base.model.BaseEntityModel;
import org.sz.mbay.channel.user.enums.TradeType;

/**
 * 交易明细
 * 
 * @author ONECITY
 * 
 */
public class MbayTradeRecord extends BaseEntityModel {

	private static final long serialVersionUID = -4530068744756661787L;

	/* 流水号 */
	private String tradeNumber;

	/* 日期 */
	private DateTime createTime;

	/* 交易名称 */
	private String tradeName;

	/* 交易金额 */
	private double amount;

	/* 交易类型：转账，充流量.... */
	private TradeType tradeType;

	/* 商户订单号 */
	private String relateNumber;

	
	
	public String getTradeNumber() {
		return tradeNumber;
	}

	public void setTradeNumber(String tradeNumber) {
		this.tradeNumber = tradeNumber;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	

	public String getRelateNumber() {
		return relateNumber;
	}

	public void setRelateNumber(String relateNumber) {
		this.relateNumber = relateNumber;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	
	

}
