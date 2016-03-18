package org.sz.mbay.session.web.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);
	
	private static final SessionManager ins = new SessionManager();
	
	// session map
	private static final Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
	
	// 定时器map
	// private static final Map<String, Timer> timerMap = new
	// ConcurrentHashMap<String, Timer>();
	
	public static SessionManager getInstance() {
		return ins;
	}
	
	/**
	 * 创建session
	 * 
	 * @param id
	 * @param timeout
	 * @param prefix
	 * @return
	 */
	public static Session createSession(String id, Integer timeout) {
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		
		LOGGER.info("start to create session id = {}", id);
		
		final Session session = new Session(id, timeout);
		sessionMap.put(id, session);
		return session;
	}
	
	public static Session getSession(String id) {
		return sessionMap.get(id);
	}
	
}
