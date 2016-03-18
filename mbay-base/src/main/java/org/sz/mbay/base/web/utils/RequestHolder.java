package org.sz.mbay.base.web.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description: 获取用户请求HttpServletRequest 对象
 * @author han.han
 * @date 2015-5-19 下午5:12:49
 * 
 */
public class RequestHolder {

	public static HttpServletRequest getServletRequest() {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			return request;
		} catch (Exception e) {
			return null;
		}
	}

}
