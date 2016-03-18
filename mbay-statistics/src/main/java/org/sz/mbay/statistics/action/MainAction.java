package org.sz.mbay.statistics.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sz.mbay.statistics.context.StatisticsLog;
import org.sz.mbay.statistics.event.ElementClick;
import org.sz.mbay.statistics.event.ElementType;
import org.sz.mbay.statistics.event.UrlEnter;

/**
 * 统计
 * 
 * @author jerry
 */
@Controller
@RequestMapping("main")
public class MainAction {
	
	/**
	 * 进入页面
	 * 
	 * @param url
	 * @param time
	 * @return
	 */
	@ResponseBody
	@RequestMapping("urlEnter")
	public Object urlEnter(
			@RequestParam("user") String user,
			@RequestParam("url") String url) {
		UrlEnter ue = new UrlEnter();
		ue.setUrl(url);
		ue.setUser(user);
		StatisticsLog.log(ue);
		return null;
	}
	
	/**
	 * 页面元素点击
	 * 
	 * @param user
	 * @param type
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping("elementClick")
	public Object elementClick(
			@RequestParam("url") String url,
			@RequestParam("user") String user,
			@RequestParam("type") ElementType type,
			@RequestParam("name") String name) {
		ElementClick ec = new ElementClick();
		ec.setEleType(type);
		ec.setUser(user);
		ec.setName(name);
		ec.setUrl(url);
		StatisticsLog.log(ec);
		return null;
	}
}
