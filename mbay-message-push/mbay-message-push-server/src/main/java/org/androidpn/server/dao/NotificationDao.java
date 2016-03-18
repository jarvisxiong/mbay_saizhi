package org.androidpn.server.dao;

import java.util.List;

import org.androidpn.server.model.Notification;

/**
 * 消息持久层
 * 
 * @author jerry
 */
public interface NotificationDao extends BaseDao<Notification> {
	
	/**
	 * 根据条件查询通知列表
	 * 
	 * @param mo
	 *            查询条件
	 * @return List<Notification>
	 */
	List<Notification> queryNotification(Notification notification);
	
	/**
	 * 根据用户名和通知ID查询通知
	 * 
	 * @param userName
	 *            用户名
	 * @param messageId
	 *            通知ID
	 * @return Notification
	 */
	Notification queryNotificationByUserName(String userName, String messageId);
	
	/**
	 * 查询用户未正常接收的通知
	 * 
	 * @param mo
	 *            查询条件
	 * @return List<Notification>
	 */
	List<Notification> queryNotification(String username);
}
