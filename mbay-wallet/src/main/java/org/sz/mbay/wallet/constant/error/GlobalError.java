package org.sz.mbay.wallet.constant.error;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

/**
 * 全局错误信息
 * 
 * @author jerry
 */
public class GlobalError {
	
	public static Response SYSTEM_ERROR = ResponseFail.create(
			"SYSTEM_ERROR", "服务器异常");
	
	public static Response SESSION_EXPIRED = ResponseFail.create(
			"SESSION_EXPIRED", "您尚未登录或登录已过期，请重新登录");
	
	public static Response PAGE_EXPIRED = ResponseFail.create(
			"PAGE_EXPIRED", "页面已过期，请重新操作");
	
	public static Response SMS_TYPE_ERROR = ResponseFail.create(
			"SMS_TYPE_ERROR", "短信类型错误");
}
