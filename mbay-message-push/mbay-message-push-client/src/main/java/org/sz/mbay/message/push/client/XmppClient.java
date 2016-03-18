package org.sz.mbay.message.push.client;

import java.util.HashMap;
import java.util.Map;

import org.androidpn.server.xmpp.push.Message;
import org.springframework.util.StringUtils;
import org.sz.mbay.common.util.HttpSupport;

/**
 * xmpp推送客户端
 * 
 * @author jerry
 */
public class XmppClient {
	
	// 服务地址(截止到项目名)
	private String xmppServer;
	
	// apikey
	private String apiKey;
	
	// 对应移动应用名称
	private String application;
	
	public XmppClient(String xmppServer, String application, String apiKey) {
		this.application = application;
		this.apiKey = apiKey;
		
		if (!StringUtils.isEmpty(xmppServer)) {
			xmppServer = xmppServer.replace("\\", "/");
			this.xmppServer = xmppServer.endsWith("/") ? xmppServer : xmppServer + "/";
		}
	}
	
	/**
	 * 向指定用户发送消息
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void pushUnicastMessage(final Message message) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apiKey", apiKey);
		params.put("application", application);
		params.put("username", message.getUsername());
		params.put("title", message.getTitle());
		params.put("content", message.getContent());
		params.put("uri", message.getUri());
		params.put("messageCategory", message.getMessageCategory().toString());
		
		String urlFinal = xmppServer + "notification/push_unicast_message.mbay";
		HttpSupport.connect(urlFinal, params);
	}
	
	/**
	 * 向所有用户发送消息
	 * 
	 * @param message
	 * @throws Exception
	 */
	public void pushMulticastMessage(final Message message) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("apiKey", apiKey);
		params.put("application", application);
		params.put("title", message.getTitle());
		params.put("content", message.getContent());
		params.put("uri", message.getUri());
		params.put("messageCategory", message.getMessageCategory().toString());
		
		String urlFinal = xmppServer + "notification/push_multicast_message.mbay";
		HttpSupport.connect(urlFinal, params);
	}
}
