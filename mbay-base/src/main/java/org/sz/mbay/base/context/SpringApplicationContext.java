package org.sz.mbay.base.context;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ONECITY
 * 		
 */
public class SpringApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext appContext;
	
	// Private constructor prevents instantiation from other classes
	private SpringApplicationContext() {
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
		
	}
	
	/**
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return appContext.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> beanType) {
		return appContext.<T> getBean(beanType);
	}
	
	public static <T> Map<String, T> getBeansOfType(Class<T> beanType) {
		return appContext.getBeansOfType(beanType);
	}
	
}
