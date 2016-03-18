package org.androidpn.server.service.impl;

import java.util.List;

import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.model.Notification;
import org.androidpn.server.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationDao notificationDao;
	
	@Override
	public void saveNotification(Notification notification) {
		notificationDao.saveOrUpdate(notification);
	}
	
	@Override
	public void updateNotification(Notification notification) {
		notificationDao.saveOrUpdate(notification);
	}
	
	@Override
	public void deleteNotification(Long id) {
		notificationDao.delete(id);
	}
	
	@Override
	public Notification queryNotificationById(Long id) {
		return notificationDao.get(id);
	}
	
	@Override
	public void createNotifications(List<Notification> notifications) {
		for (Notification notification : notifications) {
			notificationDao.saveOrUpdate(notification);
		}
	}
	
	@Override
	public Notification queryNotificationByUserName(String userName, String messageId) {
		return notificationDao.queryNotificationByUserName(userName, messageId);
	}
	
	@Override
	public List<Notification> queryNotification(Notification notification) {
		return notificationDao.queryNotification(notification);
	}
	
	@Override
	public List<Notification> queryNotification(String username) {
		return notificationDao.queryNotification(username);
	}
	
}
