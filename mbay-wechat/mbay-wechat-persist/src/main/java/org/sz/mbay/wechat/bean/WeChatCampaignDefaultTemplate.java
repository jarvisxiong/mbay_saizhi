package org.sz.mbay.wechat.bean;

import java.io.Serializable;
import org.joda.time.DateTime;

/**
 * 活动默认模板实体bean
 * @author Frank
 *
 */
@SuppressWarnings("serial")
public class WeChatCampaignDefaultTemplate implements Serializable {

	private int id; //id
	private String backPicture; //背景图片
	private String buttonPicture; //按钮图片
    private DateTime createtime; //时间
    private String username; //创建人
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBackPicture() {
		return backPicture;
	}
	public void setBackPicture(String backPicture) {
		this.backPicture = backPicture;
	}
	public String getButtonPicture() {
		return buttonPicture;
	}
	public void setButtonPicture(String buttonPicture) {
		this.buttonPicture = buttonPicture;
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