package org.sz.mbay.channel.user.qo;

import org.sz.mbay.channel.user.enums.CertStatus;
import org.sz.mbay.channel.user.enums.ChannelProperty;

public class AuthStateQO {
	
	private int userId;
	private CertStatus authState;
	private String authInfo;
	private ChannelProperty property;
	private int rowc;
	
	public int getRowc() {
		return rowc;
	}
	
	public void setRowc(int rowc) {
		this.rowc = rowc;
	}
	
	public ChannelProperty getProperty() {
		return property;
	}
	
	public void setProperty(ChannelProperty property) {
		this.property = property;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public CertStatus getAuthState() {
		return authState;
	}
	
	public void setAuthState(CertStatus authState) {
		this.authState = authState;
	}

	public String getAuthInfo() {
		return authInfo;
	}
	
	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}
}
