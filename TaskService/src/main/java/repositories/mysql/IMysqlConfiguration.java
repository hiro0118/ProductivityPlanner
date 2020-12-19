package repositories.mysql;

public interface IMysqlConfiguration {

	String getAddress();

	String getPort();

	String getDatabase();

	String getUser();

	String getPass();
}
