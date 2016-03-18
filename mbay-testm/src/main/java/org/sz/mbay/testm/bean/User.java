package org.sz.mbay.testm.bean;

import java.io.Serializable;

/**
 * 商户信息表
 * 
 * @author
 * 
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernumber() {
		return usernumber;
	}

	public void setUsernumber(String usernumber) {
		this.usernumber = usernumber;
	}

	private String username;
	
	private String usernumber;

}
