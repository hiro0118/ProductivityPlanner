package application;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService implements ITaskService {

	private final ITaskRepository repository;

	public TaskService(ITaskRepository repository) {
		this.repository = repository;
	}

	@Override
	public void createTask(ProductivityTaskDto input) throws Exception {
		ProductivityTask task = createProductivityTaskFrom(input);
		repository.save(task);
	}

	@Override
	public void updateTask(ProductivityTaskDto input) {
		ProductivityTask task = createProductivityTaskFrom(input);
		repository.update(task);
	}

	@Override
	public void deleteTask(int taskId) {
		repository.delete(taskId);
	}

	@Override
	public List<ProductivityTaskDto> getAllTasks() {
		List<ProductivityTask> tasksInRepository = repository.getAll();
		return tasksInRepository.stream().map(task -> new ProductivityTaskDto(task)).collect(Collectors.toList());
	}

	@Override
	public List<ProductivityTaskDto> getTasksOn(Date date) {
		List<ProductivityTask> tasksInRepository = repository.getAll();
		return tasksInRepository.stream()
				.filter(task -> task.getDate().equals(date))
				.map(task -> new ProductivityTaskDto(task))
				.collect(Collectors.toList());
	}

	private ProductivityTask createProductivityTaskFrom(ProductivityTaskDto input) {
		return new ProductivityTask(input.getId(), input.getTitle(), input.getDate(), input.isCompleted(),
				input.getNote(), input.getPriority(), input.getTargetTime(), input.getActualTime());
	}
}
