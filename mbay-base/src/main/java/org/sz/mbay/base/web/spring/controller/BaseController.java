package org.sz.mbay.base.web.spring.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class BaseController extends MultiActionController {
    /** 基于@ExceptionHandler异常处理 **/

    @ExceptionHandler
    public ModelAndView exp(HttpServletRequest request,
	    HttpServletResponse response, Exception ex) {
	// 页面跳转的方式，如果出错直接跳转错误页面
	if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
		.getHeader("X-Requested-With") != null && request.getHeader(
		"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
	    Map<String, Object> m = new HashMap<String, Object>();
	    m.put("errorMessage", ex.getMessage());
	    return new ModelAndView("/commons/error", m);
	} else// ajax方式调用，JSON格式返回 。目前有些ajax调用没有回调函数，设计的思路是在回调中弹出一个window，在
	      // window中显示错误信息
	{
	    try {
		PrintWriter writer = response.getWriter();
		writer.write("错误信息：" + ex.getMessage());
		writer.flush();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    return null;

	}
    }
}
