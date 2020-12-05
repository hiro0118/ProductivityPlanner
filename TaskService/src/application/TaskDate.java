package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TaskDate {

	private final Calendar calendar;
	private final String DateString;

	public TaskDate(Date date) {
		this(date.getTime());
	}

	public TaskDate(long time) {
		this.calendar =  new Calendar.Builder()
				.setInstant(time)
				.setTimeOfDay(0, 0, 0) // masks the time
				.build();
		this.DateString = buildDateFormatter().format(this.calendar);
	}

	private SimpleDateFormat buildDateFormatter() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formatter;
	}

	public Date getRawDate() {
		return calendar.getTime();
	}

	public long getTimestamp() {
		return calendar.getTimeInMillis();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "=" + DateString;
	}

	@Override
	public int hashCode() {
		return DateString.hashCode();
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
