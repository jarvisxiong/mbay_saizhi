package org.sz.mbay.stationLetter.bean;

import java.io.Serializable;
import org.joda.time.DateTime;

/**
 * 站内信
 * 
 * @author frank.zong
 * 
 */
@SuppressWarnings("serial")
public class StationLetter implements Serializable {
	
	// 消息id
	private int id;
	// 标题
	private String title;
	// 内容
	private String content;
	// 发送时间
	private DateTime sendTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public DateTime getSendTime() {
		return sendTime;
	}
	
	public void setSendTime(DateTime sendTime) {
		this.sendTime = sendTime;
	}
}
