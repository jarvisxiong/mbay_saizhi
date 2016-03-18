package org.sz.mbay.channel.web.util;

import org.joda.time.DateTime;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.common.util.MbayDateFormat.DatePattern;

/**
 * @Description:服务器系统时间
 * @author han.han
 * @date 2015-1-26 下午1:57:49
 * 
 */
public class SystemTime {
	
	/**
	 * 返回服务器端日期
	 * 
	 * @return
	 */
	public static String getNowDate() {
		return MbayDateFormat.formatDateTime(DateTime.now(), DatePattern.isoDate);
	}
	
}
