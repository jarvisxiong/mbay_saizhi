package org.sz.mbay.wechat.bean;

import java.io.Serializable;

/**
* @Description: 活动统计返回结果
* @author frank.zong
* @date 2015-1-5 上午11:27:01 
*
 */
@SuppressWarnings("serial")
public class WeChatCampaignStatistics implements Serializable {
	
	/** 未完善 ***/
	private int noneFinish;
	/** 未开始 **/
	private int notStarted;
	/** 活动中 **/
	private int inActive;
	/** 已结束 **/
	private int over;
	/** 已取消 **/
	private int cancled;
	
	public int getNoneFinish() {
		return noneFinish;
	}
	public void setNoneFinish(int noneFinish) {
		this.noneFinish = noneFinish;
	}
	public int getNotStarted() {
		return notStarted;
	}
	public void setNotStarted(int notStarted) {
		this.notStarted = notStarted;
	}
	public int getInActive() {
		return inActive;
	}
	public void setInActive(int inActive) {
		this.inActive = inActive;
	}
	public int getOver() {
		return over;
	}
	public void setOver(int over) {
		this.over = over;
	}
	public int getCancled() {
		return cancled;
	}
	public void setCancled(int cancled) {
		this.cancled = cancled;
	}
	
	public WeChatCampaignStatistics(){
		this.noneFinish = 0;
		this.notStarted = 0;
		this.inActive = 0;
		this.over = 0;
		this.cancled = 0;
	}
}