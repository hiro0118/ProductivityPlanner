package application;

import java.util.List;

public interface ITaskRepository {

	void save(ProductivityTask task);

	void update(ProductivityTask task);

	void delete(String id);

	ProductivityTask find(String id);

	List<ProductivityTask> getAll();
}
