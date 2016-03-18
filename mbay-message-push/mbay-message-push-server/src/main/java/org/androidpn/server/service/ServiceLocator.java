///*
// * Copyright (C) 2010 Moduad Co., Ltd.
// * 
// * This program is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 2 of the License, or
// * (at your option) any later version.
// * 
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// * GNU General Public License for more details.
// * 
// * You should have received a copy of the GNU General Public License along
// * with this program; if not, write to the Free Software Foundation, Inc.,
// * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
// */
//package org.androidpn.server.service;
//
//import org.androidpn.server.service.impl.NotificationServiceImpl;
//import org.androidpn.server.service.impl.UserServiceImpl;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//
///**
// * This is a helper class to look up service objects.
// * 
// * @author Sehwan Noh (devnoh@gmail.com)
// */
//public class ServiceLocator implements BeanFactoryAware {
//	
//	private static BeanFactory beanFactory = null;
//	
//	private static ServiceLocator servlocator = null;
//	
//	public void setBeanFactory(BeanFactory factory) throws BeansException {
//		ServiceLocator.beanFactory = factory;
//	}
//	
//	public BeanFactory getBeanFactory() {
//		return beanFactory;
//	}
//	
//	public static ServiceLocator getInstance() {
//		if (servlocator == null) {
//			servlocator = (ServiceLocator) getService(ServiceLocator.class);
//		}
//		return servlocator;
//	}
//	
//	/**
//	 * 根据提供的bean名称得到相应的服务类
//	 * 
//	 * @param servName
//	 *            bean名称
//	 */
//	public static Object getService(String servName) {
//		return beanFactory.getBean(servName);
//	}
//	
//	/**
//	 * 根据提供的bean名称得到对应于指定类型的服务类
//	 * 
//	 * @param servName
//	 *            bean名称
//	 * @param clazz
//	 *            返回的bean类型,若类型不匹配,将抛出异常
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static Object getService(String servName, Class clazz) {
//		return beanFactory.getBean(servName, clazz);
//	}
//	
//	/**
//	 * 根据提供的bean类型得到相应的服务类
//	 * 
//	 * @param clazz
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	public static Object getService(Class clazz) {
//		return getService(clazz.getName(), clazz);
//	}
//	
//	public static UserService getUserService() {
//		return (UserService) getService(UserServiceImpl.class);
//	}
//	
//	public static NotificationService getNotificationService() {
//		return (NotificationService) getService(NotificationServiceImpl.class);
//	}
//}
