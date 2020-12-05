package repository.mysql;

import resource.PropertyUtil;

public class MysqlConfiguration implements IMysqlConfiguration {

	private static final String ADDRESS_KEY = "mysql.address";
	private static final String PORT_KEY = "mysql.port";
	private static final String DATABASE_KEY = "mysql.database";
	private static final String USER_KEY = "mysql.user";
	private static final String PASS_KEY = "mysql.pass";

	private static final String ADDRESS_DEFAULT = "localhost";
	private static final String PORT_DEFAULT = "3306";
	private static final String DATABASE_DEFAULT = "task_service";
	private static final String USER_DEFAULT = "root";
	private static final String PASS_DEFAULT = "root";

	private final String address;
	private final String port;
	private final String database;
	private final String user;
	private final String pass;

	public MysqlConfiguration(PropertyUtil property) {
		address = property.getProperty(ADDRESS_KEY, ADDRESS_DEFAULT);
		port = property.getProperty(PORT_KEY, PORT_DEFAULT);
		database = property.getProperty(DATABASE_KEY, DATABASE_DEFAULT);
		user = property.getProperty(USER_KEY, USER_DEFAULT);
		pass = property.getProperty(PASS_KEY, PASS_DEFAULT);
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public String getPort() {
		return port;
	}

	@Override
	public String getDatabase() {
		return database;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getPass() {
		return pass;
	}
}
