package property;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyUtil {

	private final Properties properties = new Properties();

	public PropertyUtil(String filePath) {
		try (FileInputStream input = new FileInputStream(filePath)) {
			properties.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key, "");
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}