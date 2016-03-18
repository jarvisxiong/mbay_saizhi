package org.sz.mbay.session.web.callback;

import org.sz.mbay.session.web.manager.Session;

public interface ICallback {
	
	/**
	 * session过期回调
	 * 
	 * @param session
	 */
	public void handleTimeout(Session session);
}
