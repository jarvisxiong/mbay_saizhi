package org.sz.mbay.wallet.constant.error;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

/**
 * hcode 相关错误
 * 
 * @author jerry
 */
public class HcodeError {
	
	public static Response NOT_FOUND_HCODE = ResponseFail.create(
			"NOT_FOUND_HCODE", "未匹配到您的手机号归属信息，请联系客服人员");
	
	public static Response OPERATOR_MISMATCHING = ResponseFail.create(
			"OPERATOR_MISMATCHING", "号码归属地不匹配，您的号码不能兑换该流量包");
}
