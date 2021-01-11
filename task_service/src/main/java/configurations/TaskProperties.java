package configurations;

import java.io.FileInputStream;
import java.util.Properties;

public class TaskProperties {

	private final Properties properties = new Properties();

	public TaskProperties(String filePath) {
		try (FileInputStream input = new FileInputStream(filePath)) {
			properties.load(input);
		} catch (Exception e) {
			System.out.println("Error! Property file doesn't exist at " + filePath);
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key, "");
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}
