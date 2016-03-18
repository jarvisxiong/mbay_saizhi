package org.sz.mbay.base.enums;

/**
 * 活动状态
 * 
 * @author ONECITY
 * 
 */
public enum CampaignStatus {
	
	NONE_FINISH("未完善"), 
	NOT_STARTED("未开始"), 
	IN_ACTIVE("活动中"), 
	OVER("已结束"), 
	CANCLED("已取消");
	
	private String value;
	
	private CampaignStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return this.value;
	}
}
