package org.sz.mbay.channel.bean;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.sz.mbay.base.enums.EnableState;

/**
 * 邮件模板
 * 
 * @author jerry
 */
public class EmailTemplate implements Serializable {
	
	private static final long serialVersionUID = 284561844446028697L;
	
	// id
	private Integer id;
	
	// 创建时间
	private DateTime createTime;
	
	// 名称
	private String name;
	
	// 内容
	private String content;
	
	// 状态 1->启用,0->禁用
	private EnableState status;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public DateTime getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public EnableState getStatus() {
		return status;
	}
	
	public void setStatus(EnableState status) {
		this.status = status;
	}
}
