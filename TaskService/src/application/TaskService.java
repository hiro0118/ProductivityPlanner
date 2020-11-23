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
		synchronized (repository) {
			if (repository.find(task.getId()) == null) {
				repository.save(task);
			} else {
				throw new Exception("A task with the ID already exists!");
			}
		}
	}

	@Override
	public void updateTask(ProductivityTaskDto input) throws Exception {
		ProductivityTask task = createProductivityTaskFrom(input);
		synchronized (repository) {
			if (repository.find(task.getId()) != null) {
				repository.update(task);
			} else {
				throw new Exception("A task with the ID does not exists!");
			}
		}
	}

	@Override
	public boolean deleteTask(int taskId) {
		synchronized (repository) {
			ProductivityTask task = repository.find(taskId);
			if (task != null) {
				repository.delete(taskId);
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public List<ProductivityTaskDto> getAllTasks() {
		List<ProductivityTask> tasksInRepository;
		synchronized (repository) {
			tasksInRepository = repository.getAll();
		}
		return tasksInRepository.stream().map(task -> new ProductivityTaskDto(task)).collect(Collectors.toList());
	}

	@Override
	public ProductivityTaskDto getTask(int id) {
		ProductivityTask taskInRepository;
		synchronized (repository) {
			taskInRepository = repository.find(id);
		}
		return new ProductivityTaskDto(taskInRepository);
	}

	@Override
	public List<ProductivityTaskDto> getTasksOn(Date date) {
		List<ProductivityTask> tasksInRepository;
		synchronized (repository) {
			tasksInRepository = repository.getAll();
		}
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
