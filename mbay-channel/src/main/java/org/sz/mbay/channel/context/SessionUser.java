package org.sz.mbay.channel.context;

import java.io.Serializable;

import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;

public class SessionUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6778563661878503147L;
	
	/** 主键ID */
	private final long id;
	/** 编号 ***/
	private final String userNumber;
	/** 用户账户名 */
	private final String username;
	/** 账户类型 **/
	private final ChannelProperty property;
	/** 实名状态 **/
	private CertStatus certStatus;
	
	public SessionUser(final long id, final String usernumber,
			final String username, final ChannelProperty property) {
		this.userNumber = usernumber;
		this.id = id;
		this.property = property;
		this.username = username;
		
	}
	
	public long getId() {
		return id;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public String getUsername() {
		return username;
	}
	
	public ChannelProperty getProperty() {
		return property;
	}
	
	public CertStatus getCertStatus() {
		return certStatus;
	}
	
	public void setCertStatus(CertStatus certStatus) {
		this.certStatus = certStatus;
	}
	
}
