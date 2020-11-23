package test;

import application.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryTaskRepository;

import java.util.Date;
import java.util.List;

class TaskServiceTest {

	private TaskService service;

	@BeforeEach
	public void initializeService() {
		ITaskRepository repository = new InMemoryTaskRepository();
		service = new TaskService(repository);
	}

	@Test
	public void createTask_validTask_successfullyCreated() {
		ProductivityTaskDto input1 = createDtoWithId(1);
		Assertions.assertDoesNotThrow(() -> service.createTask(input1));
		ProductivityTaskDto input2 = createDtoWithId(2);
		Assertions.assertDoesNotThrow(() -> service.createTask(input2));

		List<ProductivityTaskDto> tasksInRepository = service.getAllTasks();
		Assertions.assertEquals(2, tasksInRepository.size());
		Assertions.assertTrue(tasksInRepository.contains(input1));
		Assertions.assertTrue(tasksInRepository.contains(input2));
	}

	@Test
	public void createTask_duplicate_exceptionThrown() {
		ProductivityTaskDto input1 = createDtoWithId(1);
		Assertions.assertDoesNotThrow(() -> service.createTask(input1));
		ProductivityTaskDto input2 = createDtoWithId(1);
		Exception e = Assertions.assertThrows(Exception.class, () -> service.createTask(input2));
		String actualMessage = e.getMessage();
		Assertions.assertTrue(actualMessage.contains("A task with the ID already exists!"));
	}

	@Test
	public void updateTask_updateValidTask_successfulUpdated() {
		ProductivityTaskDto input1 = createDtoWithId(1);
		Assertions.assertDoesNotThrow(() -> service.createTask(input1));
		ProductivityTaskDto update1 = new ProductivityTaskDto(1, "title_updated", new Date(1500000000), false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
		Assertions.assertDoesNotThrow(() -> service.updateTask(update1));

		List<ProductivityTaskDto> tasksInRepository = service.getAllTasks();
		Assertions.assertEquals(1, tasksInRepository.size());
		Assertions.assertEquals("title_updated", tasksInRepository.get(0).getTitle());
	}

	@Test
	public void updateTask_updateNonExistingTask_exceptionThrown() {
		ProductivityTaskDto input1 = createDtoWithId(1);
		Assertions.assertDoesNotThrow(() -> service.createTask(input1));
		ProductivityTaskDto update1 = new ProductivityTaskDto(2, "title_updated", new Date(1500000000), false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
		Exception e = Assertions.assertThrows(Exception.class, () -> service.updateTask(update1));
		String actualMessage = e.getMessage();
		Assertions.assertTrue(actualMessage.contains("A task with the ID does not exists!"));
	}

	@Test
	public void deleteTask_deleteValidTasks_successfullyDeleted() {
		ProductivityTaskDto input1 = createDtoWithId(1);
		ProductivityTaskDto input2 = createDtoWithId(2);
		ProductivityTaskDto input3 = createDtoWithId(3);
		Assertions.assertDoesNotThrow(() -> service.createTask(input1));
		Assertions.assertDoesNotThrow(() -> service.createTask(input2));
		Assertions.assertDoesNotThrow(() -> service.createTask(input3));
		Assertions.assertEquals(3, service.getAllTasks().size());

		boolean deleteResult = service.deleteTask(input2.getId());
		Assertions.assertTrue(deleteResult);
		List<ProductivityTaskDto> tasks = service.getAllTasks();
		Assertions.assertEquals(2, tasks.size());
		Assertions.assertTrue(tasks.contains(input1));
		Assertions.assertFalse(tasks.contains(input2));
		Assertions.assertTrue(tasks.contains(input3));
	}

	@Test
	public void deleteTask_deleteNonExistentTask_returnsFalse() {
		ProductivityTaskDto input1 = createDtoWithId(1);
		ProductivityTaskDto input2 = createDtoWithId(2);
		Assertions.assertDoesNotThrow(() -> service.createTask(input1));
		Assertions.assertEquals(1, service.getAllTasks().size());

		boolean deleteResult = service.deleteTask(input2.getId());
		Assertions.assertFalse(deleteResult);

		List<ProductivityTaskDto> tasks = service.getAllTasks();
		Assertions.assertTrue(tasks.contains(input1));
		Assertions.assertFalse(tasks.contains(input2));
	}

	@Test
	public void getTasksOn_getTasksForDifferentDates_returnsExpectedTask() {
		Date dateA = new Date(1500000000);
		Date dateB = new Date(1600000000);
		ProductivityTaskDto input1 = createDtoWithIdAndDate(1, dateA);
		ProductivityTaskDto input2 = createDtoWithIdAndDate(2, dateA);
		ProductivityTaskDto input3 = createDtoWithIdAndDate(3, dateB);
		Assertions.assertDoesNotThrow(() -> service.createTask(input1));
		Assertions.assertDoesNotThrow(() -> service.createTask(input2));
		Assertions.assertDoesNotThrow(() -> service.createTask(input3));

		List<ProductivityTaskDto> tasksOnDateA = service.getTasksOn(dateA);
		Assertions.assertEquals(2, tasksOnDateA.size());
		Assertions.assertTrue(tasksOnDateA.contains(input1));
		Assertions.assertTrue(tasksOnDateA.contains(input2));

		List<ProductivityTaskDto> tasksOnDateB = service.getTasksOn(dateB);
		Assertions.assertEquals(1, tasksOnDateB.size());
		Assertions.assertTrue(tasksOnDateB.contains(input3));

		List<ProductivityTaskDto> tasksOnDifferentDate = service.getTasksOn(new Date(1700000000));
		Assertions.assertEquals(0, tasksOnDifferentDate.size());
	}

	private ProductivityTaskDto createDtoWithId(int id) {
		return new ProductivityTaskDto(id, "title", new Date(1500000000), false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
	}

	private ProductivityTaskDto createDtoWithIdAndDate(int id, Date date) {
		return new ProductivityTaskDto(id, "title", date, false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
	}
}