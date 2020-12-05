package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TaskDate {

	private final Calendar calendar;
	private final String dateString;

	public TaskDate(String dateString) {
		this.calendar = buildCalendarFrom(dateString);
		this.dateString = buildDateFormatter().format(calendar.getTime());
	}

	private Calendar buildCalendarFrom(String dateString) {
		String[] dateData = dateString.split("-"); // yyyy-MM-dd
		if (dateData.length != 3) {
			throw new IllegalArgumentException("String for date is not formatted properly: " + dateString);
		}

		int year = Integer.parseInt(dateData[0]);
		int month = Integer.parseInt(dateData[1]);
		int day = Integer.parseInt(dateData[2]);
		if (!valueIsInRange(year, 2000, 3000) || !valueIsInRange(month, 1, 12) || !valueIsInRange(day, 1, 31)) {
			throw new IllegalArgumentException("String for date is not formatted properly: " + dateString);
		}

		try {
			return new Calendar.Builder()
					.setDate(year, month - 1, day)
					.setTimeZone(TimeZone.getTimeZone("GMT"))
					.build();
		} catch (Exception e) {
			throw new IllegalArgumentException("String for date is not formatted properly: " + dateString, e);
		}
	}

	private boolean valueIsInRange(int value, int min, int max) {
		return (min <= value) && (value <= max);
	}

	private SimpleDateFormat buildDateFormatter() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formatter;
	}

	public String getDateString() {
		return dateString;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "=" + dateString;
	}

	@Override
	public int hashCode() {
		return dateString.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TaskDate) {
			return obj.hashCode() == this.hashCode();
		} else {
			return false;
		}
	}
}
