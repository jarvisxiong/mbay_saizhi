package org.sz.mbay.wechat.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.base.enums.EnableState;

/**
 * 活动广告实体bean
 * @author Frank
 *
 */
@SuppressWarnings("serial")
public class WeChatCampaignAdvertise implements Serializable {

	private int id; //id
	private String picture; //广告图片
	private String name; //名称
	private EnableState status; //启用|禁用
    private DateTime createtime; //时间
    private String username; //创建人
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EnableState getStatus() {
		return status;
	}
	public void setStatus(EnableState status) {
		this.status = status;
	}
	public DateTime getCreatetime() {
		return createtime;
	}
	public void setCreatetime(DateTime createtime) {
		this.createtime = createtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}