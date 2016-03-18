package org.sz.mbay.base.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.sz.mbay.base.annotation.Token;
import org.sz.mbay.base.web.servlet.tags.token.TokenProcessor;

/**
 * 用于拦截表用token注解请求，并验证是否为重复提交
 * 
 * @author han.han
 * 
 */
public class TokenIntercept extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
	    HttpServletResponse response, Object handler) throws Exception {
	if (handler instanceof HandlerMethod) {
	    HandlerMethod handlerMethod = (HandlerMethod) handler;
	    Method method = handlerMethod.getMethod();
	    Token tokenAnnotation = method.getAnnotation(Token.class);
	    if (tokenAnnotation != null) {
		if (tokenAnnotation.save()) {// 表示需要创建token
		    TokenProcessor.getInstance().saveToken(request);
		}
		if (tokenAnnotation.reset()) {// 表示检查token
		    // System.out.println("检查是否为重复提交");
		    if (!TokenProcessor.getInstance().isTokenValid(request,
			    true)) {// 表示重复提交
			response.sendError(409, "Duplicate");
			return false;
		    }
		}
	    }
	}
	return super.preHandle(request, response, handler);
    }

}
