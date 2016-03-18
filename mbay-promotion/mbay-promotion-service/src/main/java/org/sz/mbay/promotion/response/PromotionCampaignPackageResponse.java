package org.sz.mbay.promotion.response;

import org.sz.mbay.promotion.bean.PromotionCampaignPackage;

public class PromotionCampaignPackageResponse {
	
	private boolean success;
	
	private PromotionCampaignPackage campaignPackage;
	
	private String content;
	
	public PromotionCampaignPackageResponse() {
	}
	
	public PromotionCampaignPackageResponse(
			PromotionCampaignPackage campaignPackage, String content) {
		this.campaignPackage = campaignPackage;
		this.content = content;
	}
	
	public PromotionCampaignPackageResponse(boolean success,
			PromotionCampaignPackage campaignPackage, String content) {
		this.success = success;
		this.campaignPackage = campaignPackage;
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public PromotionCampaignPackage getCampaignPackage() {
		return campaignPackage;
	}
	
	public void setCampaignPackage(PromotionCampaignPackage campaignPackage) {
		this.campaignPackage = campaignPackage;
	}
	
}
