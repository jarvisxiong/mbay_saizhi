package org.sz.mbay.base.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebContext implements ServletContextAware {
	
	public static HttpServletRequest getServletRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}
	
	public static HttpSession getHttpSession() {
		return getServletRequest().getSession();
	}
	
	private static ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		WebContext.servletContext=servletContext;
	}
	
	
	public static ServletContext getServletContext(){
		return servletContext;
	}

}
