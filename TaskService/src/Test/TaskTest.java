package Test;

import Application.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class TaskTest {

    @Test
    public void testEquals() {

        List<Task> tasks = new ArrayList<>();

        Date date = new Date(1605000000);
        TaskForTest task = new TaskForTest(1, "title", date, "note");
        TaskForTest sameTask = new TaskForTest(1, "title", date, "note");
        TaskForTest diffId = new TaskForTest(2, "title", date,"note");
        TaskForTest diffTitle = new TaskForTest(1, "diffTitle", date,"note");
        TaskForTest diffDate = new TaskForTest(1, "title", new Date(1604900000),"note");
        TaskForTest diffNote = new TaskForTest(1, "title", date,"diffNote");

        tasks.add(task);
        tasks.add(sameTask);
        tasks.add(diffId);
        tasks.add(diffTitle);
        tasks.add(diffDate);
        tasks.add(diffNote);

        tasks.forEach(t -> System.out.println(t.hashCode()));
        List<Task> matches = tasks.stream().filter(t -> t.equals(task)).collect(Collectors.toList());

        Assertions.assertEquals(2, matches.size());
        Assertions.assertTrue(matches.contains(task));
        Assertions.assertTrueg(matches.contains(sameTask));
    }

    // Class to make Task concrete for tests
    private class TaskForTest extends Task {
        private TaskForTest(int id, String title, Date date, String note) {
            super(id, title, date, note);
        }
    }
}