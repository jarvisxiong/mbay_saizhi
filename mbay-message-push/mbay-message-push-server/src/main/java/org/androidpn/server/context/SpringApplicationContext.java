package org.androidpn.server.context;

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
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Class<T> clazz) {
		return (T) appContext.getBean(beanName);
	}
	
	public static <T> T getBeanBySimpleName(Class<T> clazz) {
		return getBean(clazz.getSimpleName(), clazz); 
	}
	
	public static <T> T getBeanByDefaultName(Class<T> clazz) {
		String name = clazz.getSimpleName();
		name = name.substring(0, 1).toLowerCase() + name.substring(1);
		return getBean(name, clazz);
	}
}
