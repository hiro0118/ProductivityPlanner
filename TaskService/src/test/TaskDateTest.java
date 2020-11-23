package test;

import application.TaskDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class TaskDateTest {

	@Test
	public void equals_sameDate_returnsTrue() {
		TaskDate date1 = new TaskDate(new Date(1599955200000L)); // 9/13/2020 12:00:00 AM GMT
		TaskDate date2 = new TaskDate(new Date(1600041599000L)); // 9/13/2020 11:59:59 PM GMT
		Assertions.assertEquals(date1, date2);
	}

	@Test
	public void equals_differentDate_returnsFalse() {
		TaskDate date1 = new TaskDate(new Date(1600041599000L)); // 9/13/2020 11:59:59 PM GMT
		TaskDate date2 = new TaskDate(new Date(1600041600000L)); // 9/14/2020 12:00:00 AM GMT
		Assertions.assertNotEquals(date1, date2);
	}

	@Test
	public void toString_compareExpectedAndActual_theyMatch() {
		String expected = "TaskDate=2020/09/13";
		TaskDate date1 = new TaskDate(new Date(1599955200000L)); // 9/13/2020 12:00:00 AM GMT
		TaskDate date2 = new TaskDate(new Date(1600041599000L)); // 9/13/2020 11:59:59 PM GMT
		Assertions.assertEquals(expected, date1.toString());
		Assertions.assertEquals(expected, date2.toString());
	}

}