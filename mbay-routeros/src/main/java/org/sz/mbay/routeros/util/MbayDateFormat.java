package org.sz.mbay.routeros.util;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期格式化处理
 * 
 * @author ONECITY
 * 
 */
public class MbayDateFormat {
	
	public static class DatePattern {
		
		public static final String isoTime = "yyyy-MM-dd HH:mm:ss";
		public static final String isoDate = "yyyy-MM-dd";
		public static final String yyyyMMdd = "yyyyMMdd";
	}
	
	public static class DateFormatter {
		
		public static final DateTimeFormatter timeFormat = DateTimeFormat.forPattern(DatePattern.isoTime);
		public static final DateTimeFormatter dateFormat = DateTimeFormat.forPattern(DatePattern.isoDate);
	}
	
	/**
	 * 转换String格式时间为yyyy-MM-dd HH:mm:ss格式的Timestamp
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static DateTime stringToTime(String time) {
		return DateTime.parse(time, DateFormatter.timeFormat);
		
	}
	
	/**
	 * 转换String格式时间为yyyy-MM-dd格式的Timestamp
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static DateTime stringToDate(String time) {
		return DateTime.parse(time, DateFormatter.dateFormat);
	}
	
	/**
	 * 将datetime 转换为yyyy-MM-dd 格式的DateTime
	 * 
	 * @param time
	 * @return
	 */
	public static DateTime timeToDate(DateTime time) {
		return DateTime.parse(time.toString().substring(0, 10), DateFormatter.dateFormat);
	}
	
	/**
	 * 转换Timestamp 为指定templet 的字符串时间格式
	 * 
	 * @param time
	 * @param templet
	 * @return
	 */
	public static String formatDateTime(DateTime time, String templet) {
		DateTimeFormatter timeFormat = DateTimeFormat.forPattern(templet);
		return time.toString(timeFormat);
	}
	
	/**
	 * 判断当前时间是否位于时间区间
	 * 
	 * @param starttime
	 * @param aftertime
	 * @return
	 */
	public static int nowInTimeRange(DateTime starttime, DateTime aftertime) {
		DateTime now = DateTime.now();
		if (now.compareTo(starttime) < 0) {
			return -1;
		} else if (now.compareTo(aftertime) > 0) {
			return 1;
		}
		return 0;
	}
	
}
