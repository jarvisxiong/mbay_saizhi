package org.sz.mbay.wallet.constant.error;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

/**
 * 合作方错误
 * 
 * @author jerry
 */
public class TrafficSignError {
	
	public static Response SIGN_ERROR = ResponseFail.create(
			"SIGN_ERROR", "获取商户签名错误");
}
