package org.androidpn.server.xmpp.push;


/**
 * 消息实体
 * 
 * @author jerry
 */
public class Message {
	
	// apikey
	private String apiKey;
	
	// 消息发送的应用名
	private String application;
	
	// 接受者用户名
	private String username;
	
	// 消息标题
	private String title;
	
	// 消息内容
	private String content;
	
	// uri
	private String uri;
	
	// 消息分类，0-系统消息，1-个人消息
	private Integer messageCategory;
	
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public Integer getMessageCategory() {
		return messageCategory;
	}
	
	public void setMessageCategory(Integer messageCategory) {
		this.messageCategory = messageCategory;
	}
	
	@Override
	public String toString() {
		return "Message [apiKey=" + apiKey + ", application=" + application
				+ ", username=" + username + ", title=" + title + ", content="
				+ content + ", uri=" + uri + ", messageCategory="
				+ messageCategory + "]";
	}
}
