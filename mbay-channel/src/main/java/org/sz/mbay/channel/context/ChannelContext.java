package org.sz.mbay.channel.context;

import javax.servlet.http.HttpSession;

import org.sz.mbay.base.context.WebContext;

/**
 * @author ONECITY
 * 		
 */

public class ChannelContext {
	
	private static final String SESSION_CAHNNEL_KEY = "channel";
	
	public static SessionUser getSessionChannel() {
		HttpSession session = WebContext.getHttpSession();
		return (SessionUser) session.getAttribute(SESSION_CAHNNEL_KEY);
	}
	
	public static void setSessionChannel(SessionUser sessionUser) {
		HttpSession session = WebContext.getHttpSession();
		session.setAttribute(SESSION_CAHNNEL_KEY, sessionUser);
	}
	
	public static String getSessionAttribute(String attribute) {
		HttpSession session = WebContext.getHttpSession();
		return (String) session.getAttribute(attribute);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute(String attribute, Class<T> clazz) {
		HttpSession session = WebContext.getHttpSession();
		return (T) session.getAttribute(attribute);
	}
}
