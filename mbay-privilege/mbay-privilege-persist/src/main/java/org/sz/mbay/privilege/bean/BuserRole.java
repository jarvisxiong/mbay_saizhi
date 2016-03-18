package org.sz.mbay.privilege.bean;

import java.io.Serializable;

/**
 * 用户-角色对应表
 * 
 * @author Frank.zong
 * 		
 */
@SuppressWarnings("serial")
public class BuserRole implements Serializable {
	
	/** id ***/
	private int id;
	
	/** 用户id ***/
	private int userId;
	
	/** 角色id */
	private int roleId;
	
	/** 创建时间 */
	private String createTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}