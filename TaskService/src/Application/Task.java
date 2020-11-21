package Application;

import java.util.Date;

public abstract class Task {

    private static final int TITLE_MAX_LENGTH = 30;
    private static final int NOTE_MAX_LENGTH = 100;

    private final int id;
    private String title;
    private Date date;
    private boolean completed = false;
    private String note;

    public Task(int id, String title, Date date) {
        this(id, title, date, "");
    }

    public Task(int id, String title, Date date, String note) {
        this.id = id;
        this.title = validateTitle(title);
        this.date = date;
        this.note = validateNote(note);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append("@").append(id);
        builder.append("[title=").append(title);
        builder.append(",date=").append(date);
        builder.append(",completed=").append(completed);
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
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            return obj.hashCode() == this.hashCode();
        } else {
            return false;
        }
    }
}
