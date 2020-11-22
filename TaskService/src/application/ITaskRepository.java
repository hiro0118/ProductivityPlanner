package application;

import java.util.List;

public interface ITaskRepository {

	void save(ProductivityTask task);

	ProductivityTask find(int identity);

	List<ProductivityTask> getAll();
}
