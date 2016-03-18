package org.sz.mbay.wallet.interceptor;

import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.sz.mbay.wallet.constant.Global.SessionKey;
import org.sz.mbay.wallet.constant.error.GlobalError;
import org.sz.mbay.wallet.context.WalletContext;

import net.sf.json.JSONObject;

/**
 * 用户身份认证拦截器
 * 
 * @author jerry
 */
public class IdentifyInterceptor extends HandlerInterceptorAdapter {
	
	public static final String redirectAfterLogin = "redirectAfterLogin";
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IdentifyInterceptor.class);
			
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {
		// session过期或用户未登录执行非法操作
		if (WalletContext.getSessionUser() == null) {
			// 登陆后是否需要直接到当前拦截页面
			if (Boolean.valueOf(request.getParameter(redirectAfterLogin))) {
				String url = request.getRequestURL().toString();
				if (url.indexOf("?") == -1) {
					url += "?";
				}
				
				Iterator<String> itor = request.getParameterMap().keySet()
						.iterator();
				String key = null;
				String[] vals = null;
				while (itor.hasNext()) {
					key = itor.next();
					vals = request.getParameterMap().get(key);
					if (vals != null && vals.length > 0) {
						for (String val : vals) {
							url += key + "=" + val + "&";
						}
					}
				}
				url = url.substring(0, url.length() - 1);
				request.getSession().setAttribute(redirectAfterLogin, url);
			}
			
			String type = request.getHeader("X-Requested-With");
			
			// ajax请求
			if ("XMLHttpRequest".equalsIgnoreCase(type)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=utf-8");
				PrintWriter print = response.getWriter();
				print.write(JSONObject.fromObject(GlobalError.SESSION_EXPIRED)
						.toString());
				print.flush();
				print.close();
			}
			// 非ajax请求
			else {
				String mobile = (String) WalletContext.getHttpSession()
						.getAttribute(SessionKey.MOBILE);
				if (mobile == null) {
					mobile = "";
				}
				
				response.sendRedirect(request.getContextPath() +
						"/web/main/login/ui.mbay?mobile=" + mobile);
			}
			
			return false;
		}
		return true;
	}
	
}
