package org.sz.mbay.channel.framework.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.sz.mbay.base.exception.error.ErrorInfo;
import org.sz.mbay.base.exception.error.ErrorRepository;

/**
 * Action基类。
 * 
 * @author han.han 其中token部分参照struts1.
 */
@Controller
public class BaseAction {
	
	// 错误信息统一跳转页面
	// /public static final String PUBLIC_MSG_NOTE = "msgnote";
	// 未通过认证错误信息一跳转页面
	public static final String PUBLIC_AUTHMSG_NOTE = "authfail";
	// 错误信息提示name
	public static final String PUBLIC_MSG_NOTE_KEY = "message";
	
	public static final String REDIRECT_PUBLIC_MSG_NOTE = "redirect:/messagenote.mbay";
	
	// /**
	// * @param model
	// * @param request
	// * @return
	// */
	// @RequestMapping("messagenote")
	// public String publicMsgNote(Model model, HttpServletRequest request) {
	// if (request.getParameter(PUBLIC_MSG_NOTE_KEY) != null) {
	// try {
	// String message = new String(request.getParameter("message")
	// .getBytes("ISO-8859-1"), "UTF-8");
	// model.addAttribute(PUBLIC_MSG_NOTE_KEY, message);
	// } catch (UnsupportedEncodingException e) {
	// }
	// }
	// /// return PUBLIC_MSG_NOTE;
	// }
	
	/**
	 * 操作异常重定向
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("exception/error")
	public ModelAndView redirectErrorPage(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("error_page");
		String errorcode = request.getParameter("code");
		ErrorInfo exception = ErrorRepository.getErrorInfo(errorcode);
		view.addObject("exception", exception);
		return view;
	}
	/**
	 * 操作异常重定向
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("exception/web_inteface_error")
	public ModelAndView webIntefaceError(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("web_inteface_error");
		String errorcode = request.getParameter("code");
		ErrorInfo exception = ErrorRepository.getErrorInfo(errorcode);
		view.addObject("exception", exception);
		return view;
	}
	
	/*
	 * @InitBinder protected void initBinder(HttpServletRequest request,
	 * ServletRequestDataBinder binder) {
	 * binder.registerCustomEditor(Integer.class, null, new
	 * CustomNumberEditor(Integer.class, null, true));
	 * binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(
	 * Long.class, null, true)); binder.registerCustomEditor(byte[].class, new
	 * ByteArrayMultipartFileEditor()); SimpleDateFormat dateFormat = new
	 * SimpleDateFormat(getText( "date.format", request.getLocale()));
	 * dateFormat.setLenient(false); binder.registerCustomEditor(Date.class,
	 * null, new CustomDateEditor( dateFormat, true)); }
	 */
}
