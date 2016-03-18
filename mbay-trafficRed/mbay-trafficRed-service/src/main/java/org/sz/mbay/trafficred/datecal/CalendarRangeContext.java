package org.sz.mbay.trafficred.datecal;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import org.sz.mbay.common.util.MbayDateFormat;
import org.sz.mbay.trafficred.enums.SharkCycleType;

public class CalendarRangeContext {
	
	private CalendarRangeCalculate defaultCalculate = new DayCalendar();
	private CalendarRangeCalculate weekCalendar = new WeekCalendar();
	private CalendarRangeCalculate monthCalendar = new MonthCalendar();
	private CalendarRangeCalculate allTimeCalendar = new AllTimeCalendar();
	private SharkCycleType cycleType = null;
	private List<DateTime> dateTimes;
	
	public CalendarRangeContext(List<DateTime> times,
			SharkCycleType cycleType) {
		this.dateTimes = times;
		this.cycleType = cycleType;
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
		switch (cycleType) {
			case DAY:
				return defaultCalculate;
			case WEEK:
				return weekCalendar;
			case MONTH:
				return monthCalendar;
			case TOTAL:
				return allTimeCalendar;
			default:
			case NO_LIMITED:
		}
		
		return defaultCalculate;
	}
}
