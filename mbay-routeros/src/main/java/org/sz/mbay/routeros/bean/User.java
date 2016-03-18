package org.sz.mbay.routeros.bean;

import java.io.Serializable;

/**
 * 用户表
 * 
 * @author jerry
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = -3363108522696332245L;
	
	private Integer id;
	
	private Integer userId;
	
	private String userName;
	
	private String comments;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
