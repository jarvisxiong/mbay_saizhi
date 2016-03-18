package org.sz.mbay.base.annotation.hotlinking;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 防盗链处理
 *
 * @author jerry
 */
@Aspect
public class HotlinkingAspect implements ServletContextAware {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HotlinkingAspect.class);
			
	// session key
	private static final String HOTLINKING_NAME = "hotlinking-default";
	
	// 默认linkId
	private static String DEF_LINKID = null;
	
	/**
	 * 盗链处理
	 * 
	 * @param pj
	 * @param hlink
	 * @param map
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(hlink) and @annotation(map) and not "
			+ "@annotation(org.springframework.web.bind.annotation.ResponseBody)")
	Object doBefore(ProceedingJoinPoint pj,
			Hotlinking hlink,
			RequestMapping map) throws Throwable {
		// 获取HttpServletRequest
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if (req == null) {
			LOGGER.error("get HttpServletRequest fail");
			return pj.proceed();
		}
		
		// 检测是否存在参数不合法
		if (map.value().length < hlink.set()
				|| map.value().length < hlink.check()) {
			LOGGER.error("index out bound：(" + hlink.set() + "," + hlink.check()
					+ ")");
			return pj.proceed();
		}
		
		// 查找当前方法类型：set / check
		Type type = null;
		int matchlen = 0;
		String uri = req.getRequestURI().replace(req.getContextPath(), "");
		for (int i = 0; i < map.value().length; i++) {
			if (uri.contains(map.value()[i])) {
				if (matchlen < map.value()[i].length()) {
					type = hlink.set() == i ? Type.SET
							: hlink.check() == i ? Type.CHECK : Type.NON;
					matchlen = map.value()[i].length();
				}
			}
		}
		
		// 执行不同处理
		switch (type) {
			case SET:
				req.getSession().setAttribute(HOTLINKING_NAME, 0);
				
				// 组织所有参数
				Map<String, String[]> parameMap = req.getParameterMap();
				StringBuffer str = new StringBuffer();
				parameMap.forEach((key, value) -> {
					for (String val : value) {
						str.append(key + "=" + val + "&");
					}
				});
				str.deleteCharAt(str.length() - 1);
				
				String redUrl = uri.replace(map.value()[hlink.set()],
						map.value()[hlink.check()]) + "?" + str;
						
				return "redirect:" + redUrl;
			case CHECK:
				// 盗链
				if (req.getSession().getAttribute(HOTLINKING_NAME) == null) {
					String path = null;
					
					// 重定向地址
					if (StringUtils.isEmpty(hlink.link())) {
						if (StringUtils.isEmpty(hlink.linkId())) {
							LOGGER.error("did not provide redirect url");
							path = null;
						} else {
							path = DEF_LINKID;
						}
					} else {
						path = hlink.link();
					}
					
					// 重定向地址错误，不做处理
					if (path == null) {
						LOGGER.error("redirect url is null");
						return pj.proceed();
					}
					
					return path;
				}
				// 非盗链
				else {
					req.getSession().removeAttribute(HOTLINKING_NAME);
					return pj.proceed();
				}
			default:
				return pj.proceed();
		}
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		DEF_LINKID = servletContext.getInitParameter(Hotlinking.DEFAULT_LINKID);
		if (DEF_LINKID == null) {
			LOGGER.info("web.xml does not provide hotlinking url");
		} else {
			DEF_LINKID = DEF_LINKID.trim();
		}
	}
	
}
