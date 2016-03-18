package org.sz.mbay.channel.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.sz.mbay.channel.annotation.CertAuth;
import org.sz.mbay.channel.context.ChannelContext;

/**
 * 用户拦截CertAuth 注解，若方法上含有此注解则验证用户是否已通过实名认证
 * 
 * @author han.han
 * 
 */
public class CertAuthIntercept extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			CertAuth certAnnotation = method.getAnnotation(CertAuth.class);
			if (certAnnotation != null) {
				if (ChannelContext.getSessionChannel() == null) {
					response.sendError(550, "No authorization");//没有访问权限,请先完成实名认证
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);
	}

}
