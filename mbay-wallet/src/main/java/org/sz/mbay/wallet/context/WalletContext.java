package org.sz.mbay.wallet.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.sz.mbay.wallet.constant.Global.SessionKey;
import org.sz.mbay.wallet.entity.SessionUser;

/**
 * session上下文
 * 
 * @author jerry
 */
public class WalletContext {
	
	public static HttpServletRequest getServletRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}
	
	public static HttpSession getHttpSession() {
		return getServletRequest().getSession();
	}
	
	public static SessionUser getSessionUser() {
		HttpSession session = getHttpSession();
		return (SessionUser) session.getAttribute(SessionKey.SESSION_USER);
	}
	
}
