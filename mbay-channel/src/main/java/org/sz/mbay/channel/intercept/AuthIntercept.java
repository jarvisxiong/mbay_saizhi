package org.sz.mbay.channel.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.sz.mbay.channel.context.ChannelContext;

/**
 * 用户身份认证
 * 
 * @author ONECITY
 *		
 */
public class AuthIntercept implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthIntercept.class);
			
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
					throws Exception {
	}
	
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
			
	}
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("URL请求：" + request.getRequestURI());
		}
		if (ChannelContext.getSessionChannel() == null) {
			// 未登录跳转至登录界面
			response.sendRedirect(
					request.getContextPath() + "/channel/index.mbay");
			return false;
		}
		return true;
	}
	
}
