package org.sz.mbay.channel.util;

import org.sz.mbay.base.wrap.MsgType;

/**
 * @Description: 开发者模式-返回结果
 * @author frank.zong
 * @date 2014-12-2 下午16:14:40
 * 		
 */
public class AdvancedResult {
	
	public static final AdvancedResult ERROR_URL = new AdvancedResult(
			"ERROR_URL", "请求格式不正确", MsgType.TEXT);
	public static final AdvancedResult ERROR_CAMPAIGN_FAIL = new AdvancedResult(
			"ERROR_CAMPAIGN_FAIL", "活动编号不正确", MsgType.TEXT);
	public static final AdvancedResult ERROR_CAMPAIGN_DISABLED = new AdvancedResult(
			"ERROR_CAMPAIGN_DISABLED", "活动已禁用", MsgType.TEXT);
	public static final AdvancedResult ERROR_PID = new AdvancedResult(
			"ERROR_PID", "密钥验证不正确", MsgType.TEXT);
	public static final AdvancedResult ILLEGAL_IP = new AdvancedResult(
			"ILLEGAL_IP", "非法的ip地址", MsgType.TEXT);
	public static final AdvancedResult SYSTEM_ERROR = new AdvancedResult(
			"SYSTEM_ERROR", "系统繁忙,请稍后重试", MsgType.TEXT);
	public static final AdvancedResult SUCCESS = new AdvancedResult(true,
			"SUCCESS", "调用成功", MsgType.TEXT);
			
	private boolean success;
	private String status;
	private String msgType;
	private String content;
	
	public AdvancedResult(String status, String content, String msgType) {
		this.status = status;
		this.msgType = msgType;
		this.content = content;
	}
	
	public AdvancedResult(boolean success, String status, String content,
			String msgType) {
		this.success = success;
		this.status = status;
		this.msgType = msgType;
		this.content = content;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMsgType() {
		return msgType;
	}
	
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
