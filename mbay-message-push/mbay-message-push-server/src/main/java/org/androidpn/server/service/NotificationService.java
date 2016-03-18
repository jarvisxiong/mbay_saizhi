package org.androidpn.server.service;

import java.util.List;

import org.androidpn.server.model.Notification;

/**
 * 消息服务层
 * 
 * @author jerry
 */
public interface NotificationService {
	
	/**
	 * 保存通知信息
	 * 
	 * @param notification
	 */
	public void saveNotification(Notification notification);
	
	/**
	 * 修改通知信息
	 * 
	 * @param notification
	 */
	public void updateNotification(Notification notification);
	
	/**
	 * 删除通知
	 * 
	 * @param id
	 */
	public void deleteNotification(Long id);
	
	/**
	 * 查看通知
	 * 
	 * @param id
	 * @return Notification
	 */
	public Notification queryNotificationById(Long id);
	
	/**
	 * 批量保存通知
	 * 
	 * @param notifications
	 */
	public void createNotifications(List<Notification> notifications);
	
	/**
	 * 根据用户名和通知ID查询通知
	 * 
	 * @param userName
	 *            用户名
	 * @param messageId
	 *            通知ID
	 * @return Notification
	 */
	public Notification queryNotificationByUserName(String userName, String messageId);
	
	/**
	 * 根据条件查询通知列表
	 * 
	 * @param mo
	 *            查询条件
	 * @return List<Notification>
	 */
	public List<Notification> queryNotification(Notification notification);
	
	/**
	 * 查询用户未正常接收的通知
	 * 
	 * @param mo
	 *            查询条件
	 * @return List<Notification>
	 */
	public List<Notification> queryNotification(String username);
}
