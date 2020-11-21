package test;

import application.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class TaskTest {

    private static final int TITLE_MAX_LENGTH = 30;
    private static final int NOTE_MAX_LENGTH = 100;

    private static final String TITLE_EMPTY_ERROR = "The title cannot be null nor empty!";
    private static final String TITLE_TOO_LONG_ERROR = "The title cannot be longer than";
    private static final String NOTE_TOO_LONG_ERROR = "The note cannot be longer than";

    // Class to make Task concrete for tests
    private class TaskForTest extends Task {
        private TaskForTest(int id, String title, Date date, String note) {
            super(id, title, date, note);
        }
    }

    private Task createValidTask() {
        return new TaskForTest(1, "title", new Date(1500000000), "note");
    }

    @Test
    public void testEquals() {

        List<Task> tasks = new ArrayList<>();

        Date date = new Date(1500000000);
        TaskForTest task = new TaskForTest(1, "title", date, "note");
        TaskForTest sameTask = new TaskForTest(1, "title", date, "note");
        TaskForTest diffId = new TaskForTest(2, "title", date,"note");
        TaskForTest diffTitle = new TaskForTest(1, "diffTitle", date,"note");
        TaskForTest diffDate = new TaskForTest(1, "title", new Date(1510000000),"note");
        TaskForTest diffNote = new TaskForTest(1, "title", date,"diffNote");

        tasks.add(task);
        tasks.add(sameTask);
        tasks.add(diffId);
        tasks.add(diffTitle);
        tasks.add(diffDate);
        tasks.add(diffNote);
        List<Task> matches = tasks.stream().filter(t -> t.equals(task)).collect(Collectors.toList());

        Assertions.assertEquals(2, matches.size());
        Assertions.assertTrue(matches.contains(task));
        Assertions.assertTrue(matches.contains(sameTask));
    }

    @Test
    public void constructor_validTitle_ExceptionNotThrown() {
        String validTitle1 = "1";
        assertNoExceptionForTitle(validTitle1);

        String validTitle30 = "111111111122222222223333333333";
        assertNoExceptionForTitle(validTitle30);

        StringBuilder titleBuilder = new StringBuilder();
        for (int i = 0; i < TITLE_MAX_LENGTH; i++) {
            titleBuilder.append("a");
            assertNoExceptionForTitle(titleBuilder.toString());
        }
    }

    private void assertNoExceptionForTitle(String titleInput) {
        Assertions.assertDoesNotThrow(() -> {
            new TaskForTest(1, titleInput, new Date(1500000000), "note");
        });
    }

    @Test
    public void constructor_NullTitle_ExceptionThrown() {
        String nullTitle = null;
        assertTitleExceptionInCreation(nullTitle, IllegalArgumentException.class, TITLE_EMPTY_ERROR);
    }

    @Test
    public void constructor_EmptyTitle_ExceptionThrown() {
        String emptyTitle = "";
        assertTitleExceptionInCreation(emptyTitle, IllegalArgumentException.class, TITLE_EMPTY_ERROR);
    }

    @Test
    public void constructor_TooLongTitle_ExceptionThrown() {
        String longTitle31 = "1111111111222222222233333333334";
        assertTitleExceptionInCreation(longTitle31, IllegalArgumentException.class, TITLE_TOO_LONG_ERROR);

        String longTitle32 = "11111111112222222222333333333344";
        assertTitleExceptionInCreation(longTitle32, IllegalArgumentException.class, TITLE_TOO_LONG_ERROR);

        String longTitle40 = "1111111111222222222233333333334444444444";
        assertTitleExceptionInCreation(longTitle40, IllegalArgumentException.class, TITLE_TOO_LONG_ERROR);
    }

    private <T extends Throwable> void assertTitleExceptionInCreation(String titleInput, Class<T> expectedType, String expectedMessage) {
        T exception32 = Assertions.assertThrows(expectedType, () -> new TaskForTest(1, titleInput, new Date(1500000000), "note"));
        String actualMessage32 = exception32.getMessage();
        Assertions.assertTrue(actualMessage32.contains(expectedMessage));
    }

    @Test
    public void constructor_validNote_ExceptionNotThrown() {
        String validNote1 = "1";
        Assertions.assertDoesNotThrow(() -> { new TaskForTest(1, "title", new Date(1500000000), validNote1); });

        String validNote100 = "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999991111111111";
        Assertions.assertDoesNotThrow(() -> { new TaskForTest(1, "title", new Date(1500000000), validNote100); });

        StringBuilder noteBuilder = new StringBuilder();
        for (int i = 0; i < NOTE_MAX_LENGTH; i++) {
            noteBuilder.append("a");
            Assertions.assertDoesNotThrow(() -> {
                new TaskForTest(1, "title", new Date(1500000000), noteBuilder.toString());
            });
        }
    }

    @Test
    public void constructor_TooLongNote_ExceptionThrown() {
        String longNote101 = "11111111112222222222333333333344444444445555555555666666666677777777778888888888999999999911111111112";
        assertNoteExceptionInCreation(longNote101, IllegalArgumentException.class, NOTE_TOO_LONG_ERROR);

        String longNote102 = "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999991111111111122";
        assertNoteExceptionInCreation(longNote102, IllegalArgumentException.class, NOTE_TOO_LONG_ERROR);
    }


    private <T extends Throwable> void assertNoteExceptionInCreation(String noteInput, Class<T> expectedType, String expectedMessage) {
        T exception32 = Assertions.assertThrows(expectedType,
                () -> new TaskForTest(1, "title", new Date(1500000000), noteInput));
        String actualMessage32 = exception32.getMessage();
        Assertions.assertTrue(actualMessage32.contains(expectedMessage));
    }

    @Test
    public void setTitle_validTitle_ExceptionNotThrown() {
        Task task = createValidTask();

        String validTitle1 = "1";
        Assertions.assertDoesNotThrow(() -> task.setTitle(validTitle1));

        String validTitle30 = "111111111122222222223333333333";
        Assertions.assertDoesNotThrow(() -> task.setTitle(validTitle30));

        StringBuilder titleBuilder = new StringBuilder();
        for (int i = 0; i < TITLE_MAX_LENGTH; i++) {
            titleBuilder.append("a");
            Assertions.assertDoesNotThrow(() -> task.setTitle(titleBuilder.toString()));
        }
    }

    @Test
    public void setTitle_NullTitle_ExceptionThrown() {
        assertTitleExceptionInSetter(null, IllegalArgumentException.class, TITLE_EMPTY_ERROR);
    }

    @Test
    public void setTitle_TooLongTitle_ExceptionThrown() {
        assertTitleExceptionInSetter("", IllegalArgumentException.class, TITLE_EMPTY_ERROR);
    }

    private <T extends Throwable> void assertTitleExceptionInSetter(String titleInput, Class<T> expectedType, String expectedMessage) {
        Task task = createValidTask();
        T exception32 = Assertions.assertThrows(expectedType, () -> task.setTitle(titleInput));
        String actualMessage32 = exception32.getMessage();
        Assertions.assertTrue(actualMessage32.contains(expectedMessage));
    }

    @Test
    public void setNote_validNote_ExceptionNotThrown() {
        Task task = createValidTask();

        String validNote1 = "1";
        Assertions.assertDoesNotThrow(() -> task.setNote(validNote1));

        String validNote100 = "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999991111111111";
        Assertions.assertDoesNotThrow(() -> task.setNote(validNote100));

        StringBuilder noteBuilder = new StringBuilder();
        for (int i = 0; i < NOTE_MAX_LENGTH; i++) {
            noteBuilder.append("a");
            Assertions.assertDoesNotThrow(() -> task.setNote(noteBuilder.toString()));
        }
    }

    @Test
    public void setNote_TooLongNote_ExceptionThrown() {
        String longNote101 = "11111111112222222222333333333344444444445555555555666666666677777777778888888888999999999911111111112";
        assertNoteExceptionInSetter(longNote101, IllegalArgumentException.class, NOTE_TOO_LONG_ERROR);

        String longNote102 = "1111111111222222222233333333334444444444555555555566666666667777777777888888888899999999991111111111122";
        assertNoteExceptionInSetter(longNote102, IllegalArgumentException.class, NOTE_TOO_LONG_ERROR);
    }

    private <T extends Throwable> void assertNoteExceptionInSetter(String noteInput, Class<T> expectedType, String expectedMessage) {
        Task task = createValidTask();
        T exception32 = Assertions.assertThrows(expectedType, () -> task.setNote(noteInput));
        String actualMessage32 = exception32.getMessage();
        Assertions.assertTrue(actualMessage32.contains(expectedMessage));
    }

}