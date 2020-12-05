package test;

import application.TaskDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskDateTest {

	@Test
	void constructor_invalidDelimiter_exceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("20201204"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2020/12/04"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2020-12"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2020-12-04-01"));
	}

	@Test
	void constructor_wrongOrder_exceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("12-04-2020"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2020-30-12"));
	}

	@Test
	void constructor_outOfRange_exceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2019-12-31"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2071-01-01"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2020-0-01"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2020-13-01"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2020-01-0"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> new TaskDate("2020-01-32"));
	}

	@Test
	void constructor_insideRange_exceptionNotThrown() {
		Assertions.assertDoesNotThrow(() -> new TaskDate("2020-01-01"));
		Assertions.assertDoesNotThrow(() -> new TaskDate("2020-06-02"));
		Assertions.assertDoesNotThrow(() -> new TaskDate("2020-12-31"));
		Assertions.assertDoesNotThrow(() -> new TaskDate("2070-12-30"));
		Assertions.assertDoesNotThrow(() -> new TaskDate("2070-12-31"));
	}

	@Test
	void constructor_dayOverflow_useNextDay() {
		Assertions.assertEquals("2020-03-01", new TaskDate("2020-02-30").getDateString());
		Assertions.assertEquals("2020-12-01", new TaskDate("2020-11-31").getDateString());
	}

	@Test
	void equals_sameDate_returnsTrue() {
		TaskDate date1 = new TaskDate("2020-02-04");
		TaskDate date2 = new TaskDate("2020-2-4");
		Assertions.assertEquals(date1, date2);
	}

	@Test
	void equals_differentDate_returnsFalse() {
		TaskDate date1 = new TaskDate("2020-12-4");
		TaskDate date2 = new TaskDate("2020-12-5");
		Assertions.assertNotEquals(date1, date2);
	}

	@Test
	void toString_compareExpectedAndActual_theyMatch() {
		String expected = "TaskDate=2020-01-01";
		Assertions.assertEquals(expected, new TaskDate("2020-1-1").toString());
		Assertions.assertEquals(expected, new TaskDate("2020-1-01").toString());
		Assertions.assertEquals(expected, new TaskDate("2020-01-1").toString());
		Assertions.assertEquals(expected, new TaskDate("2020-01-01").toString());
		Assertions.assertEquals(expected, new TaskDate("02020-001-001").toString());
	}

}