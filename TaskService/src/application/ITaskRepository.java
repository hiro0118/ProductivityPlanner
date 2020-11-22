package application;

import java.util.List;

public interface ITaskRepository {

	void save(ProductivityTask task);

	void update(ProductivityTask task);

	void delete(int id);

	ProductivityTask find(int id);

	List<ProductivityTask> getAll();
}
