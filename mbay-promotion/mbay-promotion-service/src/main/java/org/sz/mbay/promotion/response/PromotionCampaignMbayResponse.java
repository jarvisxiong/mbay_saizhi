package org.sz.mbay.promotion.response;

public class PromotionCampaignMbayResponse {
	
	private boolean success;
	
	private int mbay;
	
	private String content;
	
	public PromotionCampaignMbayResponse() {
	}
	
	public PromotionCampaignMbayResponse(
			int mbay, String content) {
		this.mbay = mbay;
		this.content = content;
	}
	
	public PromotionCampaignMbayResponse(boolean success,
			int mbay, String content) {
		this.success = success;
		this.mbay = mbay;
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
	
	public int getMbay() {
		return mbay;
	}
	
	public void setMbay(int mbay) {
		this.mbay = mbay;
	}
	
}
