package org.sz.mbay.statistics.event;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sz.mbay.base.web.utils.RequestHolder;
import org.sz.mbay.base.web.utils.RequestUtil;

/**
 * 基本事件
 * 
 * @author jerry
 */
public abstract class BaseEvent {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseEvent.class);
			
	/** 用户 */
	protected String user;
	
	/** 用户ip */
	protected String clientIp;
	
	/** 项目名 */
	protected String project;
	
	/** 当前url */
	protected String url;
	
	/** 事件编码，每一种事件任意定义一个区别该事件的字符串，便于分析时使用 */
	protected String eventCode;
	
	public BaseEvent(String eventCode) {
		this.eventCode = eventCode;
		this.clientIp = RequestUtil.getIP(RequestHolder.getServletRequest());
	}
	
	public String getUser() {
		return getString(user);
	}
	
	public void setUser(String user) {
		this.user = setString(user);
	}
	
	public String getClientIp() {
		return getString(clientIp);
	}
	
	public String getEventCode() {
		return eventCode;
	}
	
	public String getProject() {
		return getString(project);
	}
	
	public String getUrl() {
		return getString(url);
	}
	
	public void setUrl(String url) {
		this.url = setString(url);
		
		// 解析项目名
		String u = url.replace("http://", "").replace("https://", "");
		int fi = u.indexOf("/") + 1;
		this.project = u.substring(fi, u.indexOf("/", fi));
	}
	
	/**
	 * 统计类型的字符串
	 * 
	 * @return
	 */
	public String statisticsString() {
		return "[" + getEventCode() + "] - [" + getUser() + "/" +
				getClientIp() + "] - [" + getProject() + "] # " + toString();
	}
	
	/**
	 * 统计类型的字符串
	 * 
	 * @return
	 */
	public abstract String toString();
	
	/**
	 * 字符串处理
	 * 
	 * @param str
	 * @return
	 */
	public String getString(String str) {
		if (StringUtils.isEmpty(str)) {
			return StringUtils.EMPTY;
		}
		return str;
	}
	
	/**
	 * 设置字符串
	 * 
	 * URLDecoder解码
	 * 
	 * @param str
	 * @return
	 */
	public String setString(String str) {
		try {
			return URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("url decoder error:{}", e.getMessage());
			return str;
		}
	}
}
