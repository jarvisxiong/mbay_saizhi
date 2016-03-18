package org.sz.mbay.privilege.bean;

import java.io.Serializable;

/**
 * 角色-权限对应表
 * 
 * @author Frank.zong
 * 		
 */
@SuppressWarnings("serial")
public class RolePrivilege implements Serializable {
	
	/** id ***/
	private int id;
	
	/** 角色id ***/
	private int roleId;
	
	/** 权限id */
	private int privilegeId;
	
	/** 创建时间 */
	private String createTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public int getPrivilegeId() {
		return privilegeId;
	}
	
	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}