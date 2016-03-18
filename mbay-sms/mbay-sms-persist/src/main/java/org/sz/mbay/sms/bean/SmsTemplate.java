package org.sz.mbay.sms.bean;

import java.io.Serializable;

import org.sz.mbay.base.enums.EnableState;
import org.sz.mbay.sms.enums.SMSServiceMethod;

/**
 * 
 * @ClassName: SMSTemplate
 * 			
 * @Description: 短信模板类 对应于数据库中sms_template表
 * 				
 * @author <a href="mailto:fenlonxiong@gmail.com">Fenlon</a>
 * 		
 * @date 2014年12月18日 下午3:13:41
 * 		
 */
public class SmsTemplate implements Serializable {
	
	private static final long serialVersionUID = 7176870546488707694L;
	
	private int id; // id
	private String type; // 短信类型
	private String createTime; // 创建时间
	private String name; // 名称
	private String content; // 内容
	private String suffix; // 后缀
	private EnableState status; // 状态 1->启用,0->禁用
	private SMSServiceMethod method; // 短信接口实现方式
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public SMSServiceMethod getMethod() {
		return method;
	}
	
	public void setMethod(SMSServiceMethod method) {
		this.method = method;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public EnableState getStatus() {
		return status;
	}
	
	public void setStatus(EnableState status) {
		this.status = status;
	}
	
}
