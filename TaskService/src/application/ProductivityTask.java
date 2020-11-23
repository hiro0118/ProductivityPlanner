package application;

import java.util.Date;

public class ProductivityTask {

	private static final int TITLE_MAX_LENGTH = 30;
	private static final int NOTE_MAX_LENGTH = 100;

	private final int id;
	private String title;
	private TaskDate date;
	private boolean completed;
	private String note;
	private TaskPriority priority;
	private int targetTime;
	private int actualTime;

	private ProductivityTask(Builder builder) {
		this.id = builder.id;
		this.title = validateTitle(builder.title);
		this.date = builder.date;
		this.completed = builder.completed;
		this.note = validateNote(builder.note);
		this.priority = builder.priority;
		this.targetTime = builder.targetTime;
		this.actualTime = builder.actualTime;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = validateTitle(title);
	}

	public TaskDate getDate() {
		return date;
	}

	public void setDate(TaskDate date) {
		this.date = date;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = validateNote(note);
	}

	private String validateTitle(String input) {
		if (input == null || input.isEmpty()) {
			throw new IllegalArgumentException("The title cannot be null nor empty!");
		}
		if (input.length() > TITLE_MAX_LENGTH) {
			throw new IllegalArgumentException("The title cannot be longer than " + TITLE_MAX_LENGTH
					+ " characters! input: " + input + ", length=" + input.length());
		}
		return input;
	}

	private String validateNote(String input) {
		if (input.length() > NOTE_MAX_LENGTH) {
			throw new IllegalArgumentException("The note cannot be longer than " + NOTE_MAX_LENGTH
					+ " characters! input=" + input + ", length=" + input.length());
		}
		return input;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public int getTargetTime() {
		return targetTime;
	}

	public void setTargetTime(int targetTime) {
		this.targetTime = targetTime;
	}

	public int getActualTime() {
		return actualTime;
	}

	public void setActualTime(int actualTime) {
		this.actualTime = actualTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName());
		builder.append("@").append(id);
		builder.append("[title=").append(title);
		builder.append(",date=").append(date);
		builder.append(",completed=").append(completed);
		builder.append(",priority=").append(priority);
		builder.append(",targetTime=").append(targetTime);
		builder.append(",actualTime=").append(actualTime);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		int result = Integer.hashCode(id);
		result = 31 * result + title.hashCode();
		result = 31 * result + date.hashCode();
		result = 31 * result + Boolean.hashCode(completed);
		result = 31 * result + note.hashCode();
		result = 31 * result + priority.hashCode();
		result = 31 * result + Integer.hashCode(targetTime);
		result = 31 * result + Integer.hashCode(actualTime);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProductivityTask) {
			return obj.hashCode() == this.hashCode();
		} else {
			return false;
		}
	}

	public static class Builder {
		// Required parameters
		private final int id;
		private final String title;
		private final TaskDate date;
		private final TaskPriority priority;

		// Optional parameters
		private boolean completed = false;
		private String note = "";
		private int targetTime = 0;
		private int actualTime = 0;

		public Builder(int id, String title, TaskDate date, TaskPriority priority) {
			this.id = id;
			this.title = title;
			this.date = date;
			this.priority = priority;
		}

		public Builder(int id, String title, Date date, TaskPriority priority) {
			this(id, title, new TaskDate(date), priority);
		}

		public Builder completed(boolean completed) {
			this.completed = completed;
			return this;
		}

		public Builder note(String note) {
			this.note = note;
			return this;
		}

		public Builder targetTime(int targetTime) {
			this.targetTime = targetTime;
			return this;
		}

		public Builder actualTime(int actualTime) {
			this.actualTime = actualTime;
			return this;
		}

		public ProductivityTask build() {
			return new ProductivityTask(this);
		}
	}
}
