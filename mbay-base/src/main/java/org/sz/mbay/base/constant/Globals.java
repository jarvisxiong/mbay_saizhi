package org.sz.mbay.base.constant;

/**
 * @Description: 全局常量
 * @author han.han
 * @date
 * 
 * 
 */
public final class Globals {
	
	/*****/
	public static final String TAGLIB_PACKAGE = "org.sz.mbay.taglib.html";
	
	/** 页面传输TOKEN name **/
	public static final String TRANSACTION_TOKEN_KEY = "org.sz.mbay.TOKEN";
	
	/** Session保存token name ***/
	public static final String TOKEN_KEY = TAGLIB_PACKAGE + ".TOKEN";
	
	/** Session中包含的验证码Name key ***/
	public static final String AUTHCODE_KEY = "org.sz.mbay.AUTHCODE";
	/** JSP页面input 验证码框Name ***/
	public static final String TRANSACTION_AUTHCODE_KEY = "AUTHCODE";
	
	// pageinfo 常量
	public static final String PAGEINFO_KEY = "pageinfo";
	
	/** 活动模板 **/
	public static final String ACTIVITY_TEMPLATE = "activityTemplate";
	
	/** 认证模板 **/
	public static final String CERTIFICATE_TEMPLATE = "certificateTemplate";
	
	// 保存已读公示栏记录的cookie名称
	public static final String READED_NOTICE_COOKIE = "readed_notice";
	
	// 保存未读公式栏数量
	public static final String UNREAD_NOTICE_NUM = "unread_notice_num";
	
	// 获取未读站内信上次请求时间
	public static final String UNREAD_MSG_LAST_TIME = "unread_msg_last_time";
	
	// 获取未读站内信数量
	public static final String UNREAD_MSG_NUM = "unread_msg_num";
}
