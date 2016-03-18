package org.sz.mbay.channel.user.bean;

import java.sql.Timestamp;

import org.sz.mbay.base.model.BaseEntityModel;

/**
 * 商户常量信息表
 * 
 * @author
 * 
 */
public class UserContext extends BaseEntityModel {
	
	private static final long serialVersionUID = 5782792514173126301L;
	/* 对应账户ID */
	private String usernumber;
	/* 短信数量 */
	private int smscount;
	/* 下级代理数量 */
	private int subagent;
	/* 营销中的策略 */
	private int event_inuse;
	
	/* 更新时间 * */
	private Timestamp updtime;
	
	public int getEvent_inuse() {
		return event_inuse;
	}
	
	public void setEvent_inuse(int event_inuse) {
		this.event_inuse = event_inuse;
	}
	
	public int getSmscount() {
		return smscount;
	}
	
	public void setSmscount(int smscount) {
		this.smscount = smscount;
	}
	
	public int getSubagent() {
		return subagent;
	}
	
	public void setSubagent(int subagent) {
		this.subagent = subagent;
	}
	
	public String getUsernumber() {
		return usernumber;
	}
	
	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}
	
	public Timestamp getUpdtime() {
		return updtime;
	}
	
	public void setUpdtime(Timestamp updtime) {
		this.updtime = updtime;
	}
	
}
