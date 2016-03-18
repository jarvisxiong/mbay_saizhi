/**
 * 
 */
package org.androidpn.server.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 通知内容实体
 * 
 * @author jerry
 */
@Entity
@Table(name = "apn_notification")
public class Notification implements Serializable {
	
	private static final long serialVersionUID = 1845362556725768545L;
	
	public static final String STATUS_NOT_SEND = "0";
	public static final String STATUS_SEND = "1";
	public static final String STATUS_RECEIVE = "2";
	public static final String STATUS_READ = "3";
	
	public Notification() {
		this.createTime = new Timestamp(System.currentTimeMillis());
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 对应应用
	@Column(name = "application", length = 64)
	private String application;
	
	// 用户名
	@Column(name = "username", length = 64)
	private String username;
	
	// 客户端ip
	@Column(name = "client_ip", length = 64)
	private String clientIp;
	
	// 资源
	@Column(name = "resource", length = 64)
	private String resource;
	
	// 消息id
	@Column(name = "message_id", length = 64)
	private String messageId;
	
	// api key
	@Column(name = "apiKey", length = 64)
	private String apiKey;
	
	// 标题
	@Column(name = "title", length = 100)
	private String title;
	
	// 消息体
	@Column(name = "message", length = 1000)
	private String message;
	
	// uri
	@Column(name = "uri", length = 200)
	private String uri;
	
	// 状态
	@Column(name = "status", columnDefinition = "varchar(10) comment '0: 未发送 1：已发送 2：已接收 3：已查看'")
	private String status;
	
	// 创建时间
	@Column(name = "create_time", updatable = false)
	private Timestamp createTime;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getClientIp() {
		return clientIp;
	}
	
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
	public String getResource() {
		return resource;
	}
	
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getMessageId() {
		return messageId;
	}
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public String getApplication() {
		return application;
	}
	
	public void setApplication(String application) {
		this.application = application;
	}
	
}
