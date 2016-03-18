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
package org.androidpn.server.xmpp.auth;

import org.androidpn.server.util.Config;

/**
 * This class represents a token that proves a user's authentication.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class AuthToken {
	
	private String username;
	
	private String userId;
	
	private String domain;
	
	private String application;
	
	/**
	 * Constucts a new AuthToken with the specified JID.
	 * 
	 * @param jid
	 *            the username or bare JID
	 */
	public AuthToken(String application, String jid, String userId) {
		this.userId = userId;
		this.application = application;
		this.username = jid;
		this.domain = Config.getString("xmpp.domain");
	}
	
	/**
	 * Returns the username.
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Returns the domain.
	 * 
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	
	public String getApplication() {
		return application;
	}
	
}
