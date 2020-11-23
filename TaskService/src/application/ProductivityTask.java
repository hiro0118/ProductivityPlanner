package application;

import java.util.Date;

public class ProductivityTask extends Task {

	private TaskPriority priority;
	private int targetTime;
	private int actualTime;

	public ProductivityTask(int id, String title, Date date, boolean completed, String note, TaskPriority priority, int targetTime, int actualTime) {
		super(id, title, date, completed, note);
		this.priority = priority;
		this.targetTime = targetTime;
		this.actualTime = actualTime;
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
		return super.toString();
	}

	@Override
	protected void appendPropertyStrings(StringBuilder builder) {
		builder.append(",priority=").append(priority);
		builder.append(",targetTime=").append(targetTime);
		builder.append(",actualTime=").append(actualTime);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + priority.hashCode();
		result = 31 * result + Integer.hashCode(targetTime);
		result = 31 * result + Integer.hashCode(targetTime);
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
}
