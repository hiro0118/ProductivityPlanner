package application;

import java.util.Date;
import java.util.List;

public interface ITaskService {

	void createTask(ProductivityTaskDto input) throws Exception;

	void updateTask(ProductivityTaskDto input);

	void deleteTask(int taskId);

	List<ProductivityTaskDto> getAllTasks();

	List<ProductivityTaskDto> getTasksOn(Date date);
}
