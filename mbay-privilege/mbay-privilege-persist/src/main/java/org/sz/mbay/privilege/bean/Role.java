package org.sz.mbay.privilege.bean;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 角色表
 * 
 * @author Frank.zong
 * 		
 */
@SuppressWarnings("serial")
public class Role implements Serializable {
	
	/** id ***/
	private int id;
	
	/** 角色名称 */
	@NotBlank
	@Size(min=1,max=50)
	private String name;
	
	/** 创建时间 **/
	private String createTime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
