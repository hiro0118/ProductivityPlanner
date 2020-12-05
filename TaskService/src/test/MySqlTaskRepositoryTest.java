package test;

import application.ITaskRepository;
import application.ProductivityTask;
import application.TaskPriority;
import org.junit.jupiter.api.*;
import repository.mysql.IMysqlConfiguration;
import repository.mysql.MySqlTaskRepository;

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
	public void findTest() {
		ITaskRepository repository = new MySqlTaskRepository(new TestMysqlConfiguration());
		ProductivityTask task = repository.find("1606686975445");
	}

	@Test
	public void getAllTest() {
		ITaskRepository repository = new MySqlTaskRepository(new TestMysqlConfiguration());
		List<ProductivityTask> task = repository.getAll();
		task.forEach(System.out::println);
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
