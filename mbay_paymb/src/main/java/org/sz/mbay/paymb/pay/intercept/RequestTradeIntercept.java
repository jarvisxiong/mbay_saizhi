package org.sz.mbay.paymb.pay.intercept;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.sz.mbay.paymb.pay.annotation.TradeType;
import org.sz.mbay.paymb.pay.context.RequestTradeHolder;

/**
 *
 * 
 * @author han.han
 * 
 */
public class RequestTradeIntercept extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			
			Method method = handlerMethod.getMethod();
			TradeType requestTrade = method.getAnnotation(TradeType.class);
			if (requestTrade != null) {
				RequestTradeHolder.setRequesTradePolicy(requestTrade.policy());
			}
		}

		return super.preHandle(request, response, handler);
	}

}
