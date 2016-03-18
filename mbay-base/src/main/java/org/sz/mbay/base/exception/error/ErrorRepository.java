package org.sz.mbay.base.exception.error;

import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.strategy.HashingStrategy;

import org.sz.mbay.base.trove.strategy.StringHashStrategy;

/**
 * @Description: 异常库，动态保存所有自定义errorinfo
 * @author han.han
 * @date 2014-10-25 上午12:47:06
 * 
 */
public class ErrorRepository {
	
	// /public static Map<String,ErrorInfo> errorrepo=new
	// HashMap<String,ErrorInfo>();
	private static HashingStrategy<String> strategy = new StringHashStrategy();
	public static TCustomHashMap<String, ErrorInfo> errorRepo = new TCustomHashMap<String, ErrorInfo>(strategy);
	private static final ErrorInfo SYSTEM_ERROR = new ErrorInfo("SYSTEM_ERROR", "抱歉，系统出错!");
	
	public static ErrorInfo getErrorInfo(String code) {
		ErrorInfo errorinfo = errorRepo.get(code);
		if (errorinfo == null) {
			return SYSTEM_ERROR;
		}
		return errorinfo;
	}
	
}
