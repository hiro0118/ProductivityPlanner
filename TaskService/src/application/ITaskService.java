package application;

import java.util.List;

public interface ITaskService {

	ProductivityTaskDto createTask(ProductivityTaskDto input) throws Exception;

	void updateTask(ProductivityTaskDto input) throws Exception;

	boolean deleteTask(String id);

	List<ProductivityTaskDto> getAllTasks();

	ProductivityTaskDto getTask(String id);

	List<ProductivityTaskDto> getTasksOn(String dateString);
}
