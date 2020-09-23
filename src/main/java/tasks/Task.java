package tasks;

import command.Command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Task class, used as a super class for other categorized task classes.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    protected LocalDate date;
    protected LocalTime time;
    protected boolean hasDate = false;
    protected boolean hasTime = false;
    /**
     * Constructor.
     * @param description Description of task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    /**
     * @return String description stored in the object.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @return Boolean indicating whether a task has been marked as done.
     */
    public boolean getIsDone() {
        return isDone;
    }
    /**
     * @return Status icon, ✘ if a task is not done, ✓ if a task is done.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }
    /**
     * marks the task as done, isDone set to true.
     */
    public void markAsDone() {
        this.isDone = true;
    }
    /**
     * @return Output format of task objects to be shown.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
    /**
     * @return Formatted input of date to store in the object.
     */
    protected String getFormattedDate() {
        return hasDate ? date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) : "";
    }
    /**
     * @return Formatted input of time.
     */
    protected String getFormattedTime() {
        return hasTime ? time.format(DateTimeFormatter.ofPattern("hh:mm:ss")) : "";
    }
    /**
     * @param date Updates the object variable date to parsed date.
     */
    private void setDate(LocalDate date) {
        this.date = date;
        hasDate = true;
    }
    /**
     * @param time Updates the object variable time to parsed time.
     */
    private void setTime(LocalTime time) {
        this.time = time;
        hasTime = true;
    }
    /**
     * Parses input parameter, to check and extract date and time.
     * @param line Contains details regarding date and time, by for deadline, at for event.
     */
    protected void setDateTime(String line) {
        LocalDate parsedDate = Command.parseDate(line);
        LocalTime parsedTime = Command.parseTime(line);
        if (parsedDate != null) {
            setDate(parsedDate);
        }
        if (parsedTime != null) {
            setTime(parsedTime);
        }
    }
}
