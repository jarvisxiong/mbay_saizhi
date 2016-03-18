package org.sz.mbay.channel.user.qo;

import java.io.Serializable;
import org.sz.mbay.channel.user.enums.ChannelProperty;

public class CertificateQO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String userNumber;
	private String userName;
	private ChannelProperty property;
	
	public ChannelProperty getProperty() {
		return property;
	}
	
	public void setProperty(ChannelProperty property) {
		this.property = property;
	}
	
	public String getUserNumber() {
		return userNumber;
	}
	
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
