package org.sz.mbay.promotion.datecal;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.promotion.enums.LimitType;

public class CalendarRangeContext {
	
	private CalendarRangeCalculate day = new DayCalendar();
	private CalendarRangeCalculate week = new WeekCalendar();
	private CalendarRangeCalculate month = new MonthCalendar();
	private CalendarRangeCalculate active = new AllTimeCalendar();
	private LimitType type = null;
	private List<DateTime> dateTimes;
	
	public CalendarRangeContext(List<DateTime> times,
			LimitType type) {
		this.dateTimes = times;
		this.type = type;
	}
	
	class DayCalendar implements CalendarRangeCalculate {
		
		@Override
		public int calculateInRange() {
			int times = 0;
			DateTime today = DateTime.now();
			for (DateTime dateTime : dateTimes) {
				if (MbayDateFormat.isSameDay(today, dateTime)) {
					times += 1;
				}
			}
			return times;
		}
		
	}
	
	class WeekCalendar implements CalendarRangeCalculate {
		
		@Override
		public int calculateInRange() {
			int times = 0;
			DateTime monday = LocalDate.now()
					.withDayOfWeek(DateTimeConstants.MONDAY)
					.toDateTimeAtCurrentTime();
			for (DateTime dateTime : dateTimes) {
				if (!dateTime.isBefore(monday)) {
					times += 1;
				}
			}
			return times;
		}
		
	}
	
	class MonthCalendar implements CalendarRangeCalculate {
		
		@Override
		public int calculateInRange() {
			int times = 0;
			DateTime firstDayOfMonth = LocalDate.now().withDayOfMonth(1)
					.toDateTimeAtCurrentTime();
			for (DateTime dateTime : dateTimes) {
				if (!dateTime.isBefore(firstDayOfMonth)) {
					times += 1;
				}
			}
			return times;
		}
		
	}
	
	class AllTimeCalendar implements CalendarRangeCalculate {
		
		@Override
		public int calculateInRange() {
			return dateTimes.size();
		}
	}
	
	public int calculateInRande() {
		CalendarRangeCalculate randeCalculate = getCalendarStrategy();
		return randeCalculate.calculateInRange();
	}
	
	private CalendarRangeCalculate getCalendarStrategy() {
		switch (type) {
			case DAY:
				return day;
			case WEEK:
				return week;
			case MONTH:
				return month;
			case ACTIVE:
				return active;
			default:
			case UNLIMITED:
		}
		
		return day;
	}
}
