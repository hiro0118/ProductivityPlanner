package application;

import java.util.Date;
import java.util.List;

public interface ITaskService {

	void createTask(ProductivityTaskDto input) throws Exception;

	void updateTask(ProductivityTaskDto input) throws Exception;

	boolean deleteTask(int taskId);

	List<ProductivityTaskDto> getAllTasks();

	ProductivityTaskDto getTask(int id);

	List<ProductivityTaskDto> getTasksOn(Date date);
}
