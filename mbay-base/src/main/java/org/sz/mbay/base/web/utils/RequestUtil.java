package org.sz.mbay.base.web.utils;

import static org.springframework.util.Assert.notNull;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * @Description:RequestUtil 方便于设置和接受cookie
 * @author han.han
 * 
 */
public final class RequestUtil {

	private static final Log log = LogFactory.getLog(RequestUtil.class);

	private RequestUtil() {
	}

	/**
	 * @param response
	 * @param name
	 *            cookieName
	 * @param value
	 *            值
	 * @param second
	 *            秒
	 */
	public static void setCookie(HttpServletResponse response, String name,
			String value, int second) {
		if (log.isDebugEnabled()) {
			log.debug("Setting cookie '" + name);
		}

		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setMaxAge(second); // 秒
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;
		if (cookies == null) {
			return returnCookie;
		}
		for (final Cookie thisCookie : cookies) {
			if (thisCookie.getName().equals(name)
					&& !"".equals(thisCookie.getValue())) {
				returnCookie = thisCookie;
				break;
			}
		}
		return returnCookie;
	}

	/**
	 * 删除cookie
	 * 
	 * @param response
	 * @param cookiename
	 */
	public static void deleteCookie(HttpServletResponse response,
			String cookiename) {
		setCookie(response, cookiename, "", 0);
	}

	/**
	 * Convenience method to get the application's URL based on request
	 * variables.
	 * 
	 * @param request
	 *            the current request
	 * @return URL to application
	 */
	public static String getAppURL(HttpServletRequest request) {
		if (request == null)
			return "";

		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80;
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80))
				|| (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}

	public static String getIP(HttpServletRequest request) {
		notNull(request,"request is requeired");
		String reqIP = request.getHeader("X-Real-IP");
		if (StringUtils.isEmpty(reqIP)) {
			reqIP = request.getRemoteAddr();
		}
		if (reqIP.equals("0:0:0:0:0:0:0:1")) {
			reqIP = "127.0.0.1";
		}
		return reqIP;
	}

}
