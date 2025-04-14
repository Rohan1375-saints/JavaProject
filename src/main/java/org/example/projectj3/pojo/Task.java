package org.example.projectj3.pojo;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private boolean isCompleted;
    private boolean isPinned;
    private String dueDate;

    /**
     * Constructor for Task
     * @param taskId
     * @param title
     * @param description
     * @param isCompleted
     * @param isPinned
     */
    public Task(int taskId, String title, String description, boolean isCompleted, boolean isPinned) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.isPinned = isPinned;
    }

    /**
     * Constructor for Task
     * @param title
     * @param description
     * @param isCompleted
     * @param isPinned
     */
    public Task(String title, String description, boolean isCompleted, boolean isPinned) {
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.isPinned = isPinned;
    }

    public Task() {
    }

    /**
     * Getter for taskId
     * @return taskId
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Setter for taskId
     * @param taskId
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Getter for title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for isCompleted
     * @return isCompleted
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Setter for isCompleted
     * @param completed
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Getter for isPinned
     * @return isPinned
     */
    public boolean isPinned() {
        return isPinned;
    }

    /**
     * Setter for isPinned
     * @param pinned
     */
    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    /**
     * Getter for dueDate
     * @return dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Setter for dueDate
     * @param dueDate
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Returns a string representation of the Task object
     * @return String
     */
    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                ", isPinned=" + isPinned +
                '}';
    }

}