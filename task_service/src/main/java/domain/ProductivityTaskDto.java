package domain;

import domain.entities.ProductivityTask;
import domain.entities.TaskPriority;

public class ProductivityTaskDto {

	private final String id;
	private final String title;
	private final String dateString; // yyyy-MM-dd
	private final boolean completed;
	private final String note;
	private final TaskPriority priority;
	private final int targetTime;
	private final int actualTime;

	public ProductivityTaskDto(String id, String title, String dateString, boolean completed, String note,
			TaskPriority priority, int targetTime,int actualTime) {
		this.id = id;
		this.title = title;
		this.dateString = dateString;
		this.completed = completed;
		this.note = note;
		this.priority = priority;
		this.targetTime = targetTime;
		this.actualTime = actualTime;
	}

	public ProductivityTaskDto(ProductivityTask source) {
		this.id = source.getId();
		this.title = source.getTitle();
		this.dateString = source.getDate().getDateString();
		this.completed = source.isCompleted();
		this.note = source.getNote();
		this.priority = source.getPriority();
		this.targetTime = source.getTargetTime();
		this.actualTime = source.getActualTime();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDateString() {
		return dateString;
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
		builder.append(",date=").append(dateString);
		builder.append(",completed=").append(completed);
		builder.append(",priority=").append(priority);
		builder.append(",targetTime=").append(targetTime);
		builder.append(",actualTime=").append(actualTime);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + title.hashCode();
		result = 31 * result + dateString.hashCode();
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
