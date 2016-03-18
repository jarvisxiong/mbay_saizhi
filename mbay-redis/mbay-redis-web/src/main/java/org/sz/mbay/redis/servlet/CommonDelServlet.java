package org.sz.mbay.redis.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.sz.mbay.redis.CommonRedis;

/**
 * CommonGetServlet
 * 作用：从redis中删除值（通用）
 */
public class CommonDelServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public CommonDelServlet() {
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
		String key = request.getParameter("key");
		PrintWriter out = response.getWriter(); 
		if(StringUtils.isEmpty(key)){
			out.println("");
		}else{
			boolean result = CommonRedis.delFromRedis(key);
			out.println(result);
		}
	}
}
