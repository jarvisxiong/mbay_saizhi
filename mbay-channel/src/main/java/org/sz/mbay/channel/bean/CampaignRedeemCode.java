package org.sz.mbay.channel.bean;

import org.joda.time.DateTime;
import org.sz.mbay.promotion.enums.RedeemCodeStu;

/**
 * 
 * @ClassName: CampaignRedeemCode
 * 
 * @Description: 门店活动兑换码信息
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date Jan 23, 2015 11:20:27 AM
 * 
 */
public class CampaignRedeemCode extends BaseEntityModel {
	
	private static final long serialVersionUID = -7899694055491852014L;
	/** 领取人手机号 **/
	private String cellPhone;
	/** 领取人入口门店 **/
	private long storeId;
	/** 兑换入口门店 **/
	private long redeemStoreId;
	/** 活动Id **/
	private long campaignId;
	/** 领取人生成的兑换码 **/
	private String redeemCode;
	/** 兑换码状态 **/
	private RedeemCodeStu status;
	/** 检查兑换码是否合法 **/
	private String checkCode;
	/** 兑换操作员 **/
	private StoreOperator operator;
	/** 兑换时间 **/
	private DateTime redeemTime;
	
	public String getCellPhone() {
		return cellPhone;
	}
	
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	
	public long getStoreId() {
		return storeId;
	}
	
	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}
	
	public long getCampaignId() {
		return campaignId;
	}
	
	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}
	
	public String getRedeemCode() {
		return redeemCode;
	}
	
	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}
	
	public RedeemCodeStu getStatus() {
		return status;
	}
	
	public void setStatus(RedeemCodeStu status) {
		this.status = status;
	}
	
	public String getCheckCode() {
		return checkCode;
	}
	
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	public long getRedeemStoreId() {
		return redeemStoreId;
	}
	
	public void setRedeemStoreId(long redeemStoreId) {
		this.redeemStoreId = redeemStoreId;
	}
	
	public DateTime getRedeemTime() {
		return redeemTime;
	}
	
	public void setRedeemTime(DateTime redeemTime) {
		this.redeemTime = redeemTime;
	}
	
	public StoreOperator getOperator() {
		return operator;
	}
	
	public void setOperator(StoreOperator operator) {
		this.operator = operator;
	}
}
