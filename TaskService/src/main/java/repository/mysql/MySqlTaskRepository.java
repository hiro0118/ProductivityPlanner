package repository.mysql;

import application.ITaskRepository;
import application.ProductivityTask;
import application.TaskDate;
import application.TaskPriority;

import java.sql.*;
import java.util.*;

public class MySqlTaskRepository implements ITaskRepository {

	private static final String TASK_TABLE = "tasks";
	private static final String[] FIELDS = new String[]{"id", "title", "task_date", "completed", "note", "priority", "target", "actual"};

	private final String insertQuery = buildInsertQuery();
	private final String updateQuery = buildUpdateQuery();
	private final String deleteQuery = buildDeleteQuery();
	private final String findQuery = buildSelectQuery();
	private final String getAllQuery = buildGetAllQuery();

	private final String URL;
	private final String USER;
	private final String PASS;

	public MySqlTaskRepository(IMysqlConfiguration config) {
		URL = "jdbc:mysql://" + config.getAddress() + ":" + config.getPort() + "/" + config.getDatabase();
		USER = config.getUser();
		PASS = config.getPass();
	}

	private static String buildInsertQuery() {
		// Builds fields and values first.
		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();
		Iterator<String> iterator = Arrays.stream(FIELDS).iterator();
		while(iterator.hasNext()) {
			fields.append(iterator.next());
			values.append("?");
			if (iterator.hasNext()) {
				fields.append(",");
				values.append(",");
			}
		}
		return ("INSERT INTO " + TASK_TABLE)
				.concat(" (" + fields.toString() + ")")
				.concat(" VALUES (" + values.toString() + ");");
	}

	private static String buildUpdateQuery() {
		// Builds the set clause first.
		StringBuilder setClause = new StringBuilder();
		Iterator<String> iterator = Arrays.stream(FIELDS).iterator();
		while(iterator.hasNext()) {
			String currentField = iterator.next();
			if (currentField.equals("id")) { continue; }
			setClause.append(currentField + "=?");
			if (iterator.hasNext()) {
				setClause.append(",");
			}
		}
		return ("UPDATE " + TASK_TABLE)
				.concat(" SET " + setClause.toString())
				.concat(" WHERE id=?;");
	}

	private static String buildDeleteQuery() {
		return "DELETE FROM " + TASK_TABLE + " WHERE id=?;";
	}

	private static String buildSelectQuery() {
		return "SELECT * FROM " + TASK_TABLE + " WHERE id=?;";
	}

	private static String buildGetAllQuery() {
		return "SELECT * FROM " + TASK_TABLE;
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER,  PASS);
	}

	@Override
	public void save(ProductivityTask task) {
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(insertQuery)) {
			statement.setString(1, task.getId());
			statement.setString(2, task.getTitle());
			statement.setString(3, task.getDate().getDateString());
			statement.setBoolean(4, task.isCompleted());
			statement.setString(5, task.getNote());
			statement.setString(6, task.getPriority().toString());
			statement.setInt(7, task.getTargetTime());
			statement.setInt(8, task.getActualTime());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(ProductivityTask task) {
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			// SET
			statement.setString(1, task.getTitle());
			statement.setString(2, task.getDate().getDateString());
			statement.setBoolean(3, task.isCompleted());
			statement.setString(4, task.getNote());
			statement.setString(5, task.getPriority().toString());
			statement.setInt(6, task.getTargetTime());
			statement.setInt(7, task.getActualTime());
			// WHERE
			statement.setString(8, task.getId());
			// execute
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String id) {
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
			statement.setString(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ProductivityTask find(String id) {
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(findQuery)) {
			statement.setString(1, id);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					return buildTaskFrom(result);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ProductivityTask> getAll() {
		List<ProductivityTask> tasks = new ArrayList<>();
		try (Connection connection = getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet result = statement.executeQuery(getAllQuery)) {
			while (result.next()) {
				tasks.add(buildTaskFrom(result));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	private ProductivityTask buildTaskFrom(ResultSet result) throws SQLException {
		String id = result.getString("id");
		String title = result.getString("title");
		String dateString = result.getString("task_date");
		boolean completed = result.getBoolean("completed");
		String note = result.getString("note");
		TaskPriority priority = TaskPriority.findMatch(result.getString("priority"));
		int target = result.getInt("target");
		int actual = result.getInt("actual");
		return new ProductivityTask.Builder(id, title, new TaskDate(dateString), priority)
				.completed(completed)
				.note(note)
				.targetTime(target)
				.actualTime(actual)
				.build();
	}
}
