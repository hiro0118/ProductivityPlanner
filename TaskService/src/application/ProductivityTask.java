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
}
