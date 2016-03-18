/**
 * 
 */
package org.androidpn.server.util;

import org.androidpn.server.model.Notification;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.xmpp.packet.IQ;

/**
 * 将生成的通知ID给notificationMO对象入库 ，方便根据此ID修改回执状态
 * 
 * @author jerry
 */
public class CopyMessageUtil {
	
	public static void IQ2Message(IQ iq, Notification notification) {
		Element root = iq.getElement();
		Attribute idAttr = root.attribute("id");
		if (idAttr != null) {
			String id = idAttr.getValue();
			notification.setMessageId(id);
		}
	}
}
