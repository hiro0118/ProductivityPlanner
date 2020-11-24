package repository;

import application.ITaskRepository;
import application.ProductivityTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskRepository implements ITaskRepository {

	private final Map<String, ProductivityTask> taskMap = new HashMap<>();

	@Override
	public synchronized void save(ProductivityTask task) {
		ProductivityTask copy = copyTask(task);
		taskMap.put(copy.getId(), copy);
	}

	@Override
	public synchronized void update(ProductivityTask task) {
		ProductivityTask copy = copyTask(task);
		taskMap.replace(copy.getId(), copy);
	}

	@Override
	public synchronized void delete(String id) {
		taskMap.remove(id);
	}

	@Override
	public synchronized ProductivityTask find(String id) {
		return taskMap.get(id);
	}

	@Override
	public synchronized List<ProductivityTask> getAll() {
		List<ProductivityTask> taskList = new ArrayList<>();
		taskMap.forEach((key, task) -> taskList.add(task));
		return taskList;
	}

	private ProductivityTask copyTask(ProductivityTask original) {
		return new ProductivityTask.Builder(original.getId(), original.getTitle(), original.getDate(), original.getPriority())
				.completed(original.isCompleted())
				.note(original.getNote())
				.targetTime(original.getTargetTime())
				.actualTime(original.getActualTime())
				.build();
	}
}
