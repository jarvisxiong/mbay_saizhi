package org.sz.mbay.wallet.constant.error;

import org.sz.mbay.base.wrap.Response;
import org.sz.mbay.base.wrap.ResponseFail;

/**
 * TrafficPackageAction 相关错误
 * 
 * @author jerry
 */
public class TrafficPackageError {
	
	public static Response PACKAGE_NOT_FOUND = ResponseFail.create(
			"PACKAGE_NOT_FOUND", "您所选择的流量包暂不可用，请选择其它流量包试试");
	
	public static Response PACKAGEID_ERROR = ResponseFail
			.create("PACKAGEID_ERROR", "流量包编号错误");
}
