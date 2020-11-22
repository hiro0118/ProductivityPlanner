package repository;

import application.ITaskRepository;
import application.ProductivityTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskRepository implements ITaskRepository {

	private final Map<Integer, ProductivityTask> taskMap = new HashMap<>();

	@Override
	public synchronized void save(ProductivityTask task) {
		ProductivityTask copy = new ProductivityTask(task.getId(), task.getTitle(), task.getDate(), task.getNote(),
				task.getPriority(), task.getTargetTime(), task.getActualTime());
		taskMap.put(copy.getId(), copy);
	}

	@Override
	public synchronized ProductivityTask find(int identity) {
		return taskMap.get(identity);
	}

	@Override
	public synchronized List<ProductivityTask> getAll() {
		List<ProductivityTask> taskList = new ArrayList<>();
		taskMap.forEach((key, task) -> taskList.add(task));
		return taskList;
	}
}
