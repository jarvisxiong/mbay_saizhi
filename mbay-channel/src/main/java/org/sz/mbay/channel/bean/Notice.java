package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.joda.time.DateTime;

/**
 * 平台公告表
 * @author Tom
 * @version 创建时间：2014-9-1上午10:23:46
 * @type 类型描述
 */
@SuppressWarnings("serial")
public class Notice implements Serializable {
	
	/* 主键ID **/
	private int id;
	
	/* 标题 **/
	private String title;
	
	/* 公告时间 **/
	private DateTime noticedate;
	
	/* 是否删除标示（0，未删除；1，删除） */
	private int flag;
	
	/* 内容 **/
	private String content;
	
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
	
	public DateTime getNoticedate() {
		return noticedate;
	}
	
	public void setNoticedate(DateTime noticedate) {
		this.noticedate = noticedate;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getFlag() {
		return flag;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
}
