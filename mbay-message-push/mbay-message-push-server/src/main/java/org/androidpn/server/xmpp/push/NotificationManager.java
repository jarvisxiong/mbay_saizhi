/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.androidpn.server.xmpp.push;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.androidpn.server.context.SpringApplicationContext;
import org.androidpn.server.model.Notification;
import org.androidpn.server.model.User;
import org.androidpn.server.service.NotificationService;
import org.androidpn.server.service.UserService;
import org.androidpn.server.service.impl.NotificationServiceImpl;
import org.androidpn.server.service.impl.UserServiceImpl;
import org.androidpn.server.util.CRC32Util;
import org.androidpn.server.util.CopyMessageUtil;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

/**
 * This class is to manage sending the notifcations to the users.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationManager {
	
	private static final String NOTIFICATION_NAMESPACE = "androidpn:iq:notification";
	
	private final Log log = LogFactory.getLog(getClass());
	
	private SessionManager sessionManager;
	
	private NotificationService notificationService;
	
	private UserService userService;
	
	/**
	 * Constructor.
	 */
	public NotificationManager() {
		sessionManager = SessionManager.getInstance();
		notificationService = SpringApplicationContext.getBeanByDefaultName(NotificationServiceImpl.class);
		userService = SpringApplicationContext.getBeanByDefaultName(UserServiceImpl.class);
	}
	
	/**
	 * 发送给所有在线用户（特定应用）
	 * 
	 * @param message
	 */
	public void sendToOnlineUsers(Message message) {
		log.debug("sendToOnlineUsers...");
		List<Notification> notifications = new ArrayList<Notification>();
		IQ notificationIQ = createNotificationIQ(message);
		String application = message.getApplication();
		
		for (ClientSession session : sessionManager.getSessions()) {
			try {
				// 筛选特定应用并检测session可用
				if (session.getApplication().equals(application)
						&& session.getPresence().isAvailable()) {
					// 创建通知对象
					Notification notification = new Notification();
					notification.setApplication(application);
					notification.setApiKey(message.getApiKey());
					notification.setTitle(message.getTitle());
					notification.setMessage(message.getContent());
					notification.setUri(message.getUri());
					notification.setUsername(session.getUsername());
					notification.setClientIp(session.getHostAddress());
					notification.setResource(session.getAddress().getResource());
					
					// 将消息的ID添加到通知对象
					CopyMessageUtil.IQ2Message(notificationIQ, notification);
					
					// 设置通知状态
					if (session.getPresence().isAvailable()) {
						notification.setStatus(Notification.STATUS_SEND);
						notificationIQ.setTo(session.getAddress());
						session.deliver(notificationIQ);
					} else {
						notification.setStatus(Notification.STATUS_NOT_SEND);
					}
					
					// 将每个通知加入集合中
					notifications.add(notification);
				}
			} catch (Exception e) {
				log.error("sendToOnlineUsers error:" + e.getMessage());
			}
		}
		
		// 批量入库
		notificationService.createNotifications(notifications);
	}
	
	/**
	 * 发送给所有用户（特定应用）
	 * 
	 * @param message
	 */
	public void sendToAllUsers(Message message) {
		log.debug("sendToAllUsers...");
		List<User> list = userService.getUsers(message.getApplication());
		for (User user : list) {
			message.setUsername(user.getUsername());
			sendToUser(message);
		}
	}
	
	/**
	 * 发送给多个指定用户（特定应用）
	 * ps:这边的用户名是注册时的用户名，不是真正区别每个用户的用户编号
	 * 
	 * @param message
	 */
	public void sendToSpecifiedUsers(Message message, String[] usernames) {
		log.debug("sendToSpecifiedUsers...");
		if (usernames != null && usernames.length > 0) {
			String application = message.getApplication();
			String uname;
			for (String username : usernames) {
				// 处理用户名
				uname = CRC32Util.getResult(username + "/" + application);
				message.setUsername(uname);
				sendToUser(message);
			}
		}
	}
	
	/**
	 * 发送给单个用户（特定应用）
	 * 
	 * @param message
	 */
	private void sendToUser(Message message) {
		log.debug("sendToUser...");
		// 用户不存在
		if (userService.getUserByUsername(message.getApplication(),
				message.getUsername()) == null) {
			return;
		}
		
		IQ notificationIQ = createNotificationIQ(message);
		
		// 创建通知对象
		Notification notification = new Notification();
		notification.setApplication(message.getApplication());
		notification.setApiKey(message.getApiKey());
		notification.setTitle(message.getTitle());
		notification.setMessage(message.getContent());
		notification.setUri(message.getUri());
		message.setApplication(message.getApplication());
		notification.setUsername(message.getUsername());
		
		// 将消息的ID添加到通知对象
		CopyMessageUtil.IQ2Message(notificationIQ, notification);
		
		// 用户在线
		ClientSession session = sessionManager.getSession(message.getUsername());
		if (session != null && session.getPresence().isAvailable()) {
			try {
				notificationIQ.setTo(session.getAddress());
				session.deliver(notificationIQ);
				
				notification.setStatus(Notification.STATUS_SEND);
				notification.setClientIp(session.getHostAddress());
				notification.setResource(session.getAddress().getResource());
			} catch (Exception e) {
				log.error("sendToUser error:" + e.getMessage());
			}
		}
		// 用户不在线
		else {
			notification.setStatus(Notification.STATUS_NOT_SEND);
		}
		
		// 保存数据库
		notificationService.saveNotification(notification);
	}
	
	/**
	 * 发送离线消息（特定应用）
	 * ps:会在用户上线前检测，若有未发送的消息，则执行此方法
	 * 
	 * @param notification
	 */
	public void sendOfflineNotification(Notification notification) {
		log.debug("sendOfflineNotifcation()...");
		Message msg = new Message();
		msg.setApiKey(notification.getApiKey());
		msg.setTitle(notification.getTitle());
		msg.setContent(notification.getMessage());
		msg.setUri(notification.getUri());
		IQ notificationIQ = createNotificationIQ(msg);
		
		// 将生产通知ID替换成原来的ID
		notificationIQ.setID(notification.getMessageId());
		ClientSession session = sessionManager.getSession(notification.getUsername());
		if (session != null && session.getPresence().isAvailable()) {
			notificationIQ.setTo(session.getAddress());
			session.deliver(notificationIQ);
			try {
				notification.setStatus(Notification.STATUS_SEND);
				notification.setClientIp(session.getHostAddress());
				notification.setResource(session.getAddress().getResource());
				notificationService.updateNotification(notification);
			} catch (Exception e) {
				log.warn("update notification status failure!");
			}
		}
	}
	
	/**
	 * Creates a new notification IQ and returns it.
	 */
	private IQ createNotificationIQ(Message msg) {
		Random random = new Random();
		String id = Integer.toHexString(random.nextInt());
		
		Element notification = DocumentHelper.createElement(QName.get(
				"notification", NOTIFICATION_NAMESPACE));
		notification.addElement("id").setText(id);
		notification.addElement("apiKey").setText(msg.getApiKey());
		notification.addElement("title").setText(msg.getTitle());
		notification.addElement("message").setText(msg.getContent());
		notification.addElement("uri").setText(msg.getUri());
		
		Integer msgCategory = msg.getMessageCategory();
		if (msgCategory == null) {
			msgCategory = 0;
		}
		notification.addElement("messageCategory").setText(msgCategory.toString());
		
		IQ iq = new IQ();
		iq.setType(IQ.Type.set);
		iq.setChildElement(notification);
		
		return iq;
	}
}
