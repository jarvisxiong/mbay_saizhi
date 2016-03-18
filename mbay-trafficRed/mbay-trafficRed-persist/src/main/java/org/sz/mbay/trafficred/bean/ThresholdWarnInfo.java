package org.sz.mbay.trafficred.bean;

import java.io.Serializable;

public class ThresholdWarnInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8258402957975386327L;
	
	private String campaignName;
	
	private String warningMobile;
	
	private int threshold;
	
	private boolean isWarned;
	
	

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	

	public String getWarningMobile() {
		return warningMobile;
	}

	public void setWarningMobile(String warningMobile) {
		this.warningMobile = warningMobile;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public boolean isWarned() {
		return isWarned;
	}
	

	public void setWarned(boolean isWarned) {
		this.isWarned = isWarned;
	}
	
	
	
	
	
	

}
