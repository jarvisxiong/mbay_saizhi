package org.sz.mbay.common.util;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期格式化处理
 * 
 * 
 * @author ONECITY
 * 
 */
public class MbayDateFormat {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MbayDateFormat.class);

	public static class DatePattern {

		public static final String isoTime = "yyyy-MM-dd HH:mm:ss";
		public static final String isoDate = "yyyy-MM-dd";
		public static final String slashDate = "yyyy/MM/dd";
		public static final String slashTime = "yyyy/MM/dd HH:mm:ss";
		public static final String yyyyMMdd = "yyyyMMdd";
		public static final String HHmmss = "HH:mm:ss";
		public static final String HHmm = "HH:mm";
	}

	public static class DateFormatter {

		public static final DateTimeFormatter timeFormat = DateTimeFormat
				.forPattern(DatePattern.isoTime);
		public static final DateTimeFormatter dateFormat = DateTimeFormat
				.forPattern(DatePattern.isoDate);
		public static final DateTimeFormatter slashDateFormat = DateTimeFormat
				.forPattern(DatePattern.slashDate);
		public static final DateTimeFormatter slashTimeFormat = DateTimeFormat
				.forPattern(DatePattern.slashTime);
		public static final DateTimeFormatter HHmmFormat = DateTimeFormat
				.forPattern(DatePattern.HHmm);
		public static final DateTimeFormatter HHmmssFormat = DateTimeFormat
				.forPattern(DatePattern.HHmmss);
	}

	/**
	 * 转换String格式时间为yyyy-MM-dd HH:mm:ss格式的Timestamp
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static DateTime stringToTime(String time) {
		try {
			return DateTime.parse(time, DateFormatter.timeFormat);
		} catch (IllegalArgumentException e) {
			LOGGER.error("String:{} transferToTime Error：{}", time,
					e.getMessage());
		}
		return null;

	}

	/**
	 * 转换String格式时间为HH:mm格式的Timestamp
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static DateTime stringToHHmm(String time) {
		return DateTime.parse(time, DateFormatter.HHmmFormat);

	}

	/**
	 * 转换String格式时间为yyyy-MM-dd格式的Timestamp
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static DateTime stringToDate(String time) {
		try {
			return DateTime.parse(time, DateFormatter.dateFormat);
		} catch (IllegalArgumentException e) {
			LOGGER.error("String:{} transferToDate Error：{}", time,
					e.getMessage());
		}
		return null;
	}
	
	/**
	 * 转换String格式时间为yyyy-MM-dd格式的Timestamp
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static DateTime stringToSlashDate(String time) {
		try {
			return DateTime.parse(time, DateFormatter.slashDateFormat);
		} catch (IllegalArgumentException e) {
			LOGGER.error("String:{} transferToSlashDate Error：{}", time,
					e.getMessage());
		}
		return null;
	}

	/**
	 * 将datetime 转换为yyyy-MM-dd 格式的DateTime
	 * 
	 * @param time
	 * @return
	 */
	public static DateTime timeToDate(DateTime time) {
		return stringToDate(time.toString().substring(0, 10));
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
		if (now.isAfter(aftertime)) {
			return 1;
		} else if (now.isBefore(starttime)) {
			return -1;
		}
		return 0;
	}

	public static boolean isSameDay(DateTime date1, DateTime date2) {
		DateTime d1 = timeToDate(date1);
		DateTime d2 = timeToDate(date2);
		if (d1.isBefore(d2) || d1.isAfter(d2)) {
			return false;
		}
		return true;
	}

}
