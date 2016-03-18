package org.androidpn.server.dao.impl;

import java.util.List;

import org.androidpn.server.dao.NotificationDao;
import org.androidpn.server.model.Notification;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationDaoImpl extends BaseDaoImpl<Notification> implements NotificationDao {
	
	@Override
	public List<Notification> queryNotification(Notification notification) {
		List<Notification> list = getHibernateTemplate().findByExample(notification);
		return list;
	}
	
	@Override
	public Notification queryNotificationByUserName(String userName, String messageId) {
		Object[] params = new Object[] { userName, messageId };
		String query = " from Notification where username=? and messageId=? order by createTime desc ";
		return selectOne(query, params);
	}
	
	@Override
	public List<Notification> queryNotification(String username) {
		String query = " from Notification where username=? and " +
				" status=0 order by createTime desc ";
		return selectList(query, username);
	}
	
}
