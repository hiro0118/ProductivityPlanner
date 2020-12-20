import domain.entities.ProductivityTask;
import domain.entities.TaskPriority;
import org.junit.jupiter.api.*;
import repositories.mysql.IMysqlConfiguration;
import repositories.mysql.MySqlTaskRepository;

import java.util.List;

class MySqlTaskRepositoryTest {

	private static MySqlTaskRepository repository = new MySqlTaskRepository(new TestMysqlConfiguration());

	@BeforeEach
	public void resetDataBaseBeforeEach() {
		List<ProductivityTask> tasks = repository.getAll();
		tasks.forEach(task -> repository.delete(task.getId()));
	}

	@AfterAll
	public static void resetDataBaseAfterAll() {
		List<ProductivityTask> tasks = repository.getAll();
		tasks.forEach(task -> repository.delete(task.getId()));
	}

	@Test
	public void save_twoTasks_saved() {
		ProductivityTask task1 = new ProductivityTask.Builder("id1", "title1", "2020-12-4", TaskPriority.MOST_IMPORTANT)
				.completed(true)
				.note("note1")
				.targetTime(1)
				.actualTime(2)
				.build();
		ProductivityTask task2 = new ProductivityTask.Builder("id2", "title2", "2020-12-5", TaskPriority.SECONDARY)
				.completed(false)
				.note("note2")
				.targetTime(3)
				.actualTime(4)
				.build();

		repository.save(task1);
		repository.save(task2);

		List<ProductivityTask> tasks = repository.getAll();
		Assertions.assertEquals(2, tasks.size());
		Assertions.assertTrue(tasks.contains(task1));
		Assertions.assertTrue(tasks.contains(task2));
	}

	@Test
	public void update_updateProperties_updated() {
		ProductivityTask task1 = new ProductivityTask.Builder("common_id", "title1", "2020-12-4", TaskPriority.MOST_IMPORTANT)
				.completed(true)
				.note("note1")
				.targetTime(1)
				.actualTime(2)
				.build();
		ProductivityTask task2 = new ProductivityTask.Builder("common_id", "title2", "2020-12-5", TaskPriority.SECONDARY)
				.completed(false)
				.note("note2")
				.targetTime(3)
				.actualTime(4)
				.build();

		List<ProductivityTask> tasks;

		repository.save(task1);
		tasks = repository.getAll();
		Assertions.assertEquals(1, tasks.size());
		Assertions.assertTrue(tasks.contains(task1));
		Assertions.assertFalse(tasks.contains(task2));

		repository.update(task2);
		tasks = repository.getAll();
		Assertions.assertEquals(1, tasks.size());
		Assertions.assertFalse(tasks.contains(task1));
		Assertions.assertTrue(tasks.contains(task2));
	}

	@Test
	public void delete_deleteTwoTasks_deleted() {
		ProductivityTask task1 = new ProductivityTask.Builder("id1", "title1", "2020-12-4", TaskPriority.MOST_IMPORTANT)
				.completed(true)
				.note("note1")
				.targetTime(1)
				.actualTime(2)
				.build();
		ProductivityTask task2 = new ProductivityTask.Builder("id2", "title2", "2020-12-5", TaskPriority.SECONDARY)
				.completed(false)
				.note("note2")
				.targetTime(3)
				.actualTime(4)
				.build();

		List<ProductivityTask> tasks;

		repository.save(task1);
		repository.save(task2);
		tasks = repository.getAll();
		Assertions.assertEquals(2, tasks.size());
		Assertions.assertTrue(tasks.contains(task1));
		Assertions.assertTrue(tasks.contains(task2));

		repository.delete(task1.getId());
		tasks = repository.getAll();
		Assertions.assertEquals(1, tasks.size());
		Assertions.assertFalse(tasks.contains(task1));
		Assertions.assertTrue(tasks.contains(task2));

		repository.delete(task2.getId());
		tasks = repository.getAll();
		Assertions.assertEquals(0, tasks.size());
		Assertions.assertFalse(tasks.contains(task1));
		Assertions.assertFalse(tasks.contains(task2));
	}

	@Test
	public void find_saveTasksAndFindThem_tasksFound() {
		ProductivityTask task1 = new ProductivityTask.Builder("id1", "title1", "2020-01-01", TaskPriority.MOST_IMPORTANT)
				.completed(true)
				.note("note1")
				.targetTime(1)
				.actualTime(2)
				.build();
		ProductivityTask task2 = new ProductivityTask.Builder("id2", "title2", "2020-01-02", TaskPriority.SECONDARY)
				.completed(false)
				.note("note2")
				.targetTime(2)
				.actualTime(3)
				.build();
		repository.save(task1);
		repository.save(task2);
		Assertions.assertEquals(task1, repository.find("id1"));
		Assertions.assertEquals(task2, repository.find("id2"));
		Assertions.assertNull(repository.find("id3"));
	}

	@Test
	public void getAll_saveTasksAndGetAll_allFound() {
		ProductivityTask task1 = new ProductivityTask.Builder("id1", "title1", "2020-01-01", TaskPriority.MOST_IMPORTANT)
				.completed(true)
				.note("note1")
				.targetTime(1)
				.actualTime(2)
				.build();
		ProductivityTask task2 = new ProductivityTask.Builder("id2", "title2", "2020-01-02", TaskPriority.SECONDARY)
				.completed(false)
				.note("note2")
				.targetTime(2)
				.actualTime(3)
				.build();
		repository.save(task1);
		repository.save(task2);
		List<ProductivityTask> tasks = repository.getAll();
		Assertions.assertEquals(2, tasks.size());
		Assertions.assertTrue(tasks.contains(task1));
		Assertions.assertTrue(tasks.contains(task2));
	}

	@Test
	public void getAll_saveIncrementally_allFound() {
		List<ProductivityTask> tasks;
		ProductivityTask task1 = new ProductivityTask.Builder("id1", "title1", "2020-01-01", TaskPriority.MOST_IMPORTANT)
				.completed(true)
				.note("note1")
				.targetTime(1)
				.actualTime(2)
				.build();
		repository.save(task1);
		tasks = repository.getAll();
		Assertions.assertEquals(1, tasks.size());
		Assertions.assertTrue(tasks.contains(task1));

		ProductivityTask task2 = new ProductivityTask.Builder("id2", "title2", "2020-01-02", TaskPriority.SECONDARY)
				.completed(false)
				.note("note2")
				.targetTime(2)
				.actualTime(3)
				.build();
		repository.save(task2);
		tasks = repository.getAll();
		Assertions.assertEquals(2, tasks.size());
		Assertions.assertTrue(tasks.contains(task1));
		Assertions.assertTrue(tasks.contains(task2));

		ProductivityTask task3 = new ProductivityTask.Builder("id3", "title3", "2020-01-03", TaskPriority.ADDITIONAL)
				.completed(true)
				.note("note3")
				.targetTime(3)
				.actualTime(4)
				.build();
		repository.save(task3);
		tasks = repository.getAll();
		Assertions.assertEquals(3, tasks.size());
		Assertions.assertTrue(tasks.contains(task1));
		Assertions.assertTrue(tasks.contains(task2));
		Assertions.assertTrue(tasks.contains(task3));
	}

	private static class TestMysqlConfiguration implements IMysqlConfiguration {

		@Override
		public String getAddress() {
			return "localhost";
		}

		@Override
		public String getPort() {
			return "3306";
		}

		@Override
		public String getDatabase() {
			return "test_task_service";
		}

		@Override
		public String getUser() {
			return "root";
		}

		@Override
		public String getPass() {
			return "root";
		}
	}
}
