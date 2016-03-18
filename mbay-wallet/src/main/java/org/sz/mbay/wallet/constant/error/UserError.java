package org.sz.mbay.wallet.constant.error;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

/**
 * UserAction 相关错误信息
 * 
 * @author jerry
 */
public class UserError {
	
	public static Response UNAME_EMPTY_REJECT = ResponseFail.create(
			"UNAME_EMPTY_REJECT", "手机号不能为空");
			
	public static Response UNAME_FORMAT_ERROR = ResponseFail.create(
			"UNAME_FORMAT_ERROR", "手机号格式不正确");
			
	public static Response PASSWD_EMPTY_REJECT = ResponseFail.create(
			"PASSWD_EMPTY_REJECT", "密码不能为空");
}
