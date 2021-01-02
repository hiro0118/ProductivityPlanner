package domain;

import domain.entities.ProductivityTask;
import domain.entities.TaskDate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskService implements ITaskService {

	private final ITaskRepository repository;

	public TaskService(ITaskRepository repository) {
		this.repository = repository;
	}

	@Override
	public ProductivityTaskDto createTask(ProductivityTaskDto input) throws Exception {
		ProductivityTask createdTask;
		synchronized (repository) {
			String id = generateUniqueId(repository);
			createdTask = createTaskFromDtoWithId(input, id);
			repository.save(createdTask);
		}
		return new ProductivityTaskDto(createdTask);
	}

	private String generateUniqueId(ITaskRepository repository) {
		List<ProductivityTask> tasks = repository.getAll();
		while (true) {
			String randomId = UUID.randomUUID().toString();
			boolean uniqueIdGenerated = tasks.stream().noneMatch(task -> task.getId().equals(randomId));
			if (uniqueIdGenerated) { return randomId; }
		}
	}

	@Override
	public void updateTask(ProductivityTaskDto input) throws Exception {
		ProductivityTask task = createTaskFromDto(input);
		synchronized (repository) {
			if (repository.find(task.getId()) != null) {
				repository.update(task);
			} else {
				throw new Exception("A task with the ID does not exists!");
			}
		}
	}

	@Override
	public boolean deleteTask(String id) {
		synchronized (repository) {
			ProductivityTask task = repository.find(id);
			if (task != null) {
				repository.delete(id);
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
	public ProductivityTaskDto getTask(String id) {
		ProductivityTask taskInRepository;
		synchronized (repository) {
			taskInRepository = repository.find(id);
		}
		return taskInRepository == null ? null : new ProductivityTaskDto(taskInRepository);
	}

	@Override
	public List<ProductivityTaskDto> getTasksOn(String dateString) {
		List<ProductivityTask> tasksInRepository;
		synchronized (repository) {
			tasksInRepository = repository.getAll();
		}
		TaskDate taskDate = new TaskDate(dateString);
		return tasksInRepository.stream()
				.filter(task -> task.getDate().equals(taskDate))
				.map(task -> new ProductivityTaskDto(task))
				.collect(Collectors.toList());
	}

	private ProductivityTask createTaskFromDto(ProductivityTaskDto input) {
		return new ProductivityTask.Builder(input.getId(), input.getTitle(), input.getDateString(), input.getPriority())
				.completed(input.isCompleted())
				.note(input.getNote())
				.targetTime(input.getTargetTime())
				.actualTime(input.getActualTime())
				.build();
	}

	private ProductivityTask createTaskFromDtoWithId(ProductivityTaskDto input, String id) {
		return new ProductivityTask.Builder(id, input.getTitle(), input.getDateString(), input.getPriority())
				.completed(input.isCompleted())
				.note(input.getNote())
				.targetTime(input.getTargetTime())
				.actualTime(input.getActualTime())
				.build();
	}
}
