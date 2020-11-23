package application;

import java.util.Date;

public class ProductivityTaskDto {

	private final int id;
	private final String title;
	private final Date date;
	private final boolean completed;
	private final String note;
	private final TaskPriority priority;
	private final int targetTime;
	private final int actualTime;

	public ProductivityTaskDto(int id, String title, Date date, boolean completed, String note,
			TaskPriority priority, int targetTime,int actualTime) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.completed = completed;
		this.note = note;
		this.priority = priority;
		this.targetTime = targetTime;
		this.actualTime = actualTime;
	}

	public ProductivityTaskDto(ProductivityTask source) {
		this.id = source.getId();
		this.title = source.getTitle();
		this.date = source.getDate();
		this.completed = source.isCompleted();
		this.note = source.getNote();
		this.priority = source.getPriority();
		this.targetTime = source.getTargetTime();
		this.actualTime = source.getActualTime();
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getDate() {
		return date;
	}

	public boolean isCompleted() {
		return completed;
	}

	public String getNote() {
		return note;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public int getTargetTime() {
		return targetTime;
	}

	public int getActualTime() {
		return actualTime;
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
		result = 31 * result + Integer.hashCode(targetTime);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProductivityTaskDto) {
			return obj.hashCode() == this.hashCode();
		} else {
			return false;
		}
	}
}
