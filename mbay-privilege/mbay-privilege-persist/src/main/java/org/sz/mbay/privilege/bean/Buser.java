package org.sz.mbay.privilege.bean;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户表
 * 
 * @author Frank.zong
 * 		
 */
@SuppressWarnings("serial")
public class Buser implements Serializable {
	
	/** id ***/
	private int id;
	
	/** 用户名称 */
	@NotBlank
	@Size(min=1,max=50)
	private String name;
	
	/** 昵称 */
	@NotBlank
	@Size(min=1,max=50)
	private String nick;
	
	/** 创建时间 **/
	private String createTime;
	
	/** 密码 */
	private String password;
	
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
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}