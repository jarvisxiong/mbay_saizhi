package org.sz.mbay.session.web.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring上下文
 * 
 * @author jerry
 */
public class SpringApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext appContext;
	
	private SpringApplicationContext() {
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
	}
	
	public static Object getBean(String beanName) {
		return appContext.getBean(beanName);
	}
}
