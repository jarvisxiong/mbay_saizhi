/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class represents the basic user object.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
@Entity
@Table(name = "apn_user")
public class User implements Serializable {
	
	private static final long serialVersionUID = 4733464888738356502L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 对应应用
	@Column(name = "application", length = 64)
	private String application;
	
	// 用户名
	@Column(name = "username", nullable = false, length = 64)
	private String username;
	
	// 用户id
	@Column(name = "user_id", nullable = false, length = 64)
	private String userId;
	
	// 密码
	@Column(name = "password", length = 64)
	private String password;
	
	// 创建日期
	@Column(name = "created_date", updatable = false)
	private Date createdDate = new Date();
	
	@Transient
	private boolean online;
	
	public User() {
	}
	
	public User(final String username) {
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		
		final User obj = (User) o;
		if (username != null ? !username.equals(obj.username)
				: obj.username != null) {
			return false;
		}
		if (!(createdDate.getTime() == obj.createdDate.getTime())) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		result = 29 * result + (username != null ? username.hashCode() : 0);
		result = 29 * result
				+ (createdDate != null ? createdDate.hashCode() : 0);
		return result;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
