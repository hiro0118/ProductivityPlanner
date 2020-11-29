package repository;

import application.ITaskRepository;
import application.ProductivityTask;
import application.TaskPriority;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MySqlTaskRepository implements ITaskRepository {

	// TODO Create a property file for these.
	private static final String URL = "jdbc:mysql://localhost:3306/taskservice";
	private static final String USER = "root";
	private static final String PASS = "root";

	private static final String TASK_TABLE = "task";
	private static final String[] FIELDS = new String[]{"id", "title", "date", "completed", "note", "priority", "target", "actual"};

	private final String insertQuery;
	private final String updateQuery;
	private final String deleteQuery;
	private final String findQuery;
	private final String getAllQuery;

	public MySqlTaskRepository() {
		insertQuery = buildInsertQuery();
		updateQuery = buildUpdateQuery();
		deleteQuery = buildDeleteQuery();
		findQuery = buildSelectQuery();
		getAllQuery = buildGetAllQuery();
	}

	private String buildInsertQuery() {
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

	private String buildUpdateQuery() {
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

	private String buildDeleteQuery() {
		return "DELETE FROM " + TASK_TABLE + " WHERE id=?;";
	}

	private String buildSelectQuery() {
		return "SELECT * FROM " + TASK_TABLE + " WHERE id=?;";
	}

	private String buildGetAllQuery() {
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
			statement.setDate(3, new Date(task.getDate().getRawDate().getTime()));
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
			statement.setDate(2, new Date(task.getDate().getRawDate().getTime()));
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
		Date date = result.getDate("date");
		boolean completed = result.getBoolean("completed");
		String note = result.getString("note");
		TaskPriority priority = TaskPriority.valueOf(result.getString("priority"));
		int target = result.getInt("target");
		int actual = result.getInt("actual");
		return new ProductivityTask.Builder(id, title, date, priority)
				.completed(completed)
				.note(note)
				.targetTime(target)
				.actualTime(actual)
				.build();
	}
}
