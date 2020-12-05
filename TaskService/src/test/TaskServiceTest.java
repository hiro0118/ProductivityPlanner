package test;

import application.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.inmemory.InMemoryTaskRepository;

import java.util.*;

class TaskServiceTest {

	private TaskService service;

	@BeforeEach
	public void initializeService() {
		ITaskRepository repository = new InMemoryTaskRepository();
		service = new TaskService(repository);
	}

	@Test
	public void createTask_validTask_successfullyCreated() {
		ProductivityTaskDto input1 = createDtoWithTitle("title1");
		Assertions.assertDoesNotThrow(() -> service.createTask(input1));
		ProductivityTaskDto input2 = createDtoWithTitle("title2");
		Assertions.assertDoesNotThrow(() -> service.createTask(input2));

		List<ProductivityTaskDto> tasksInRepository = service.getAllTasks();
		Assertions.assertEquals(2, tasksInRepository.size());
		Assertions.assertTrue(tasksInRepository.stream().anyMatch(task -> task.getTitle().equals(input1.getTitle())));
		Assertions.assertTrue(tasksInRepository.stream().anyMatch(task -> task.getTitle().equals(input2.getTitle())));
	}

	@Test
	public void updateTask_updateValidTask_successfulUpdated() {
		ProductivityTaskDto input1 = createDtoWithTitle("title1");
		ProductivityTaskDto output1 = registerTaskWithService(service, input1);
		ProductivityTaskDto input2 = createDtoWithTitle("title2");
		ProductivityTaskDto output2 = registerTaskWithService(service, input2);

		ProductivityTaskDto update1 = new ProductivityTaskDto(output1.getId(), "title1_updated", "2020-12-4", false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
		Assertions.assertDoesNotThrow(() -> service.updateTask(update1));

		List<ProductivityTaskDto> tasksInRepository = service.getAllTasks();
		Assertions.assertEquals(2, tasksInRepository.size());
		Assertions.assertEquals("title1_updated", service.getTask(output1.getId()).getTitle());
		Assertions.assertEquals("title2", service.getTask(output2.getId()).getTitle());
	}

	@Test
	public void updateTask_updateNonExistingTask_exceptionThrown() {
		ProductivityTaskDto input1 = createDtoWithTitle("title1");
		ProductivityTaskDto output1 = registerTaskWithService(service, input1);
		ProductivityTaskDto update2 = new ProductivityTaskDto("randomId", "title_updated", "2020-12-4", false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
		Exception e = Assertions.assertThrows(Exception.class, () -> service.updateTask(update2));
		String actualMessage = e.getMessage();
		Assertions.assertTrue(actualMessage.contains("A task with the ID does not exists!"));
		Assertions.assertEquals(1, service.getAllTasks().size());
	}

	@Test
	public void deleteTask_deleteValidTasks_successfullyDeleted() {
		// Creates 3 tasks.
		ProductivityTaskDto input1 = createDtoWithTitle("title1");
		ProductivityTaskDto input2 = createDtoWithTitle("title2");
		ProductivityTaskDto input3 = createDtoWithTitle("title3");
		ProductivityTaskDto output1 = registerTaskWithService(service, input1);
		ProductivityTaskDto output2 = registerTaskWithService(service, input2);
		ProductivityTaskDto output3 = registerTaskWithService(service, input3);
		Assertions.assertNotNull(output1);
		Assertions.assertNotNull(output2);
		Assertions.assertNotNull(output3);
		Assertions.assertEquals(3, service.getAllTasks().size());

		// Deletes the second task.
		boolean deleteResult = service.deleteTask(output2.getId());
		Assertions.assertTrue(deleteResult);

		// Checks tasks.
		List<ProductivityTaskDto> tasksAfterDeletion = service.getAllTasks();
		Assertions.assertEquals(2, tasksAfterDeletion.size());
		Assertions.assertNotNull(service.getTask(output1.getId()));
		Assertions.assertNull(service.getTask(output2.getId()));
		Assertions.assertNotNull(service.getTask(output3.getId()));
	}

	@Test
	public void deleteTask_deleteNonExistentTask_returnsFalse() {
		for (int i = 0; i < 10; i++) {
			ProductivityTaskDto input = createDtoWithTitle("title" + i);
			Assertions.assertDoesNotThrow(() -> service.createTask(input));
		}
		Assertions.assertEquals(10, service.getAllTasks().size());

		String randomId = UUID.randomUUID().toString();
		boolean deleteResult = service.deleteTask(randomId);
		Assertions.assertFalse(deleteResult);
	}

	@Test
	public void getTasksOn_getTasksForDifferentDates_returnsExpectedTask() {
		String dateA = "2020-12-4";
		String dateB = "2020-12-5";
		ProductivityTaskDto input1 = createDtoWithTitleAndDate("title1", dateA);
		ProductivityTaskDto input2 = createDtoWithTitleAndDate("title2", dateA);
		ProductivityTaskDto input3 = createDtoWithTitleAndDate("title3", dateB);
		ProductivityTaskDto output1 = registerTaskWithService(service, input1);
		ProductivityTaskDto output2 = registerTaskWithService(service, input2);
		ProductivityTaskDto output3 = registerTaskWithService(service, input3);

		List<ProductivityTaskDto> tasksOnDateA = service.getTasksOn(dateA);
		Assertions.assertEquals(2, tasksOnDateA.size());
		Assertions.assertTrue(tasksOnDateA.contains(output1));
		Assertions.assertTrue(tasksOnDateA.contains(output2));

		List<ProductivityTaskDto> tasksOnDateB = service.getTasksOn(dateB);
		Assertions.assertEquals(1, tasksOnDateB.size());
		Assertions.assertTrue(tasksOnDateB.contains(output3));

		List<ProductivityTaskDto> tasksOnDifferentDate = service.getTasksOn("2020-12-6");
		Assertions.assertEquals(0, tasksOnDifferentDate.size());
	}

	private ProductivityTaskDto createDtoWithTitle(String title) {
		return new ProductivityTaskDto("", title, "2020-12-4", false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
	}

	private ProductivityTaskDto createDtoWithTitleAndDate(String title, String date) {
		return new ProductivityTaskDto("", title, date, false, "note",
				TaskPriority.MOST_IMPORTANT, 0, 0);
	}

	private ProductivityTaskDto registerTaskWithService(ITaskService service, ProductivityTaskDto input) {
		try {
			return service.createTask(input);
		} catch (Exception e) {
			Assertions.fail("Exception thrown in task creation!");
			return null;
		}
	}
}