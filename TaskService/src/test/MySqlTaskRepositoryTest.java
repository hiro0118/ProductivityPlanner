package test;

import application.ITaskRepository;
import application.ProductivityTask;
import application.TaskPriority;
import org.junit.jupiter.api.Test;
import repository.MySqlTaskRepository;

import java.util.Date;
import java.util.List;

class MySqlTaskRepositoryTest {

	@Test
	public void saveTest() {
		ITaskRepository repository = new MySqlTaskRepository();
		Date date = new Date();
		ProductivityTask task = new ProductivityTask.Builder(Long.toString(date.getTime()), "title1040", date,TaskPriority.SECONDARY).completed(true).note("note").targetTime(3).actualTime(4).build();
		repository.save(task);
	}

	@Test
	public void updateTest() {
		ITaskRepository repository = new MySqlTaskRepository();
		Date date = new Date();
		ProductivityTask task = new ProductivityTask.Builder("1606628397487", "title2", date,TaskPriority.SECONDARY).completed(false).note("note2").targetTime(4).actualTime(54).build();
		repository.update(task);
	}

	@Test
	public void deleteTest() {
		ITaskRepository repository = new MySqlTaskRepository();
		repository.delete("1606628397487");
	}

	@Test
	public void findTest() {
		ITaskRepository repository = new MySqlTaskRepository();
		ProductivityTask task = repository.find("1606628431292");
		System.out.println("match=" + task);
	}

	@Test
	public void getALlTest() {
		ITaskRepository repository = new MySqlTaskRepository();
		List<ProductivityTask> task = repository.getAll();
		task.forEach(System.out::println);
	}
}
