package configurations;

import domain.ITaskRepository;
import domain.ITaskService;
import domain.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repositories.mysql.IMysqlConfiguration;
import repositories.mysql.MySqlTaskRepository;
import repositories.mysql.MysqlConfiguration;

@Configuration
public class TaskDependencyConfiguration {

	private static final String PROPERTY_PATH = "./TaskService/target/classes/task_service.properties";

	@Bean
	public ITaskRepository mySqlTaskRepository() {
		TaskProperties property = new TaskProperties(PROPERTY_PATH);
		IMysqlConfiguration config = new MysqlConfiguration(property);
		return new MySqlTaskRepository(config);
	}

	@Bean
	public ITaskService taskService() {
		return new TaskService(mySqlTaskRepository());
	}
}
