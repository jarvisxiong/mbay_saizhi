package org.sz.mbay.trafficred.drawlot.simple;

import org.sz.mbay.trafficred.drawlot.Response;

/**
 * 返回结果
 * 
 * @author jerry
 */
public class SimpleResponse extends Response {
	
	public SimpleResponse(Boolean status, String code, String content) {
		super(status, code, content);
	}
	
	public static final Response HIT = new SimpleResponse(true, "HIT", "中奖");
	public static final Response NOT_HIT = new SimpleResponse(true, "NOT_HIT", "未中奖");
	public static final Response PARAM_ERROR = new SimpleResponse(false, "PARAM_ERROR", "参数错误");
	public static final Response RATIO_ERROR = new SimpleResponse(false, "RATIO_ERROR", "中奖几率错误");
}
