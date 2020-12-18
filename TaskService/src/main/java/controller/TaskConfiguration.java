package controller;

import application.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import property.PropertyUtil;
import repository.mysql.IMysqlConfiguration;
import repository.mysql.MySqlTaskRepository;
import repository.mysql.MysqlConfiguration;

@Configuration
public class TaskConfiguration {

	private static final String PROPERTY_PATH = "./TaskService/target/classes/task_service.properties";

	@Bean
	public MySqlTaskRepository mySqlTaskRepository() {
		PropertyUtil property = new PropertyUtil(PROPERTY_PATH);
		IMysqlConfiguration config = new MysqlConfiguration(property);
		return new MySqlTaskRepository(config);
	}

	@Bean
	public TaskService taskService() {
		return new TaskService(mySqlTaskRepository());
	}


}
