package org.sz.mbay.redis.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.sz.mbay.redis.CommonRedis;

/**
 * CommonSetServlet
 * 作用：存值到redis中（通用）
 */
public class CommonSetServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public CommonSetServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String key = "";
		String value = "";
		String seconds = "";
		String className = "";
		Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            String paramValue = request.getParameter(paramName);
            if("key".equals(paramName)){
            	key = paramValue;
            }else if("value".equals(paramName)){
            	value = paramValue;
            }else if("className".equals(paramName)){
            	className = paramValue;
            }else if("seconds".equals(paramName)){
            	seconds = paramValue;
            }
        }
        if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)){
        	out.println(false);
        }else{
        	boolean result = CommonRedis.setToRedis(key, value + "|" + className, seconds);
        	out.println(result);
        }
	}
}
