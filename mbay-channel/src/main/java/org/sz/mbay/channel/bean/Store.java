package org.sz.mbay.channel.bean;

import java.util.List;

/**
 * 
 * @ClassName: Store
 * 
 * @Description: 门店实体
 * 
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 
 * @date Jan 23, 2015 11:18:44 AM
 * 
 */
public class Store extends BaseEntityModel {
	
	private static final long serialVersionUID = -6525302630540268801L;
	/** 门店编号 **/
	private String number;
	/** 门店名称 **/
	private String name;
	/** 门店授权码 **/
	private String authCode;
	/** 门店是否被删除 **/
	private boolean deleted;
	/** 门店所属商户 **/
	private String userNumber;
	/** 门店状态（禁用启用） **/
	private boolean enable;
	/** 门店操作员是否被激活 **/
	private boolean active;
	/** 店面操作员数量 **/
	private int operatorNum;
	/** 门店参与的活动记录 **/
	private List<StoreCampaignRecord> activityRecords;
	/** 门店二维码 **/
	private long qrCodeId;
	
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
	
	public String getAuthCode() {
		return authCode;
	}
	
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public boolean isEnable() {
		return enable;
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getOperatorNum() {
		return operatorNum;
	}
	
	public void setOperatorNum(int operatorNum) {
		this.operatorNum = operatorNum;
	}
	
	public List<StoreCampaignRecord> getActivityRecords() {
		return activityRecords;
	}
	
	public void setActivityRecords(List<StoreCampaignRecord> activityRecords) {
		this.activityRecords = activityRecords;
	}
	
	public long getQrCodeId() {
		return qrCodeId;
	}
	
	public void setQrCodeId(long qrCodeId) {
		this.qrCodeId = qrCodeId;
	}
	
}
