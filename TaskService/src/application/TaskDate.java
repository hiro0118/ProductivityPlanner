package application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TaskDate {

	private final Date date;
	private final String DateString;

	public TaskDate(Date date) {
		this.date = new Date(date.getTime());
		this.DateString = buildDateFormatter().format(this.date);
	}

	private SimpleDateFormat buildDateFormatter() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return formatter;
	}

	public Date getDate() {
		return new Date(date.getTime());
	}

	@Override
	public String toString() {
		String builder = getClass().getSimpleName() + "=" + DateString;
		return builder;
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
