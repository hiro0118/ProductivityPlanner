package configuration;

import application.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.mysql.IMysqlConfiguration;
import repository.mysql.MySqlTaskRepository;
import repository.mysql.MysqlConfiguration;

@Configuration
public class TaskDependencyConfiguration {

	private static final String PROPERTY_PATH = "./TaskService/target/classes/task_service.properties";

	@Bean
	public MySqlTaskRepository mySqlTaskRepository() {
		TaskProperties property = new TaskProperties(PROPERTY_PATH);
		IMysqlConfiguration config = new MysqlConfiguration(property);
		return new MySqlTaskRepository(config);
	}

	@Bean
	public TaskService taskService() {
		return new TaskService(mySqlTaskRepository());
	}
}
