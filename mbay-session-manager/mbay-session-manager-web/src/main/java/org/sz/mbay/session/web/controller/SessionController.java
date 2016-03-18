package org.sz.mbay.session.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.session.base.entity.ResponseData;
import org.sz.mbay.session.base.entity.ResponseFailData;
import org.sz.mbay.session.base.entity.ResponseSucData;
import org.sz.mbay.session.web.manager.Session;
import org.sz.mbay.session.web.manager.SessionManager;

/**
 * session控制器
 * 
 * @author jerry
 */
@Controller
@RequestMapping("session")
public class SessionController {
	
	/**
	 * 设置属性
	 * 
	 * @param sid
	 * @param prefix
	 * @param key
	 * @param data
	 * @param timeout
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "attr/set", method = RequestMethod.POST)
	public ResponseData attrSet(
			@RequestParam("jsessionid") String sid,
			@RequestParam("prefix") String prefix,
			@RequestParam("key") String key,
			@RequestParam("data") String data,
			@RequestParam("timeout") Integer timeout) {
		if (StringUtils.isEmpty(sid)) {
			return ResponseFailData.create("session id cannot be null");
		}
		
		Session session = SessionManager.getSession(sid);
		if (session == null) {
			session = SessionManager.createSession(sid, timeout);
		}
		
		session.setAttribute(formatKey(prefix, key), data);
		return ResponseData.SUCCESS;
	}
	
	/**
	 * 获取属性
	 * 
	 * @param sid
	 * @param prefix
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "attr/get", method = RequestMethod.GET)
	public ResponseData attrGet(
			@RequestParam("jsessionid") String sid,
			@RequestParam("prefix") String prefix,
			@RequestParam("key") String key) {
		Session session = SessionManager.getSession(sid);
		if (session == null) {
			return ResponseFailData.create("session is not exist or expired");
		}
		return ResponseSucData.create(session.getAttribute(formatKey(prefix, key)));
	}
	
	/**
	 * 获取属性
	 * 
	 * @param sid
	 * @param prefix
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "attr/remove", method = RequestMethod.GET)
	public ResponseData attrRemove(
			@RequestParam("jsessionid") String sid,
			@RequestParam("prefix") String prefix,
			@RequestParam("key") String key) {
		Session session = SessionManager.getSession(sid);
		if (session == null) {
			return ResponseFailData.create("session is not exist or expired");
		}
		session.removeAttribute(formatKey(prefix, key));
		return ResponseData.SUCCESS;
	}
	
	/*-------------------------------------------------------
	 *                      私有方法
	 *-----------------------------------------------------*/
	
	private String formatKey(String prefix, String key) {
		return StringUtils.isEmpty(prefix) ? key : prefix + "." + key;
	}
}
