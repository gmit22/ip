package tasks;

import command.Command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    protected LocalDate date;
    protected LocalTime time;
    protected boolean hasDate = false;
    protected boolean hasTime = false;

    public Task(String description) {
        this.description = description;
        this.isDone = false;

    }
    public String getDescription() {
        return description;
    }
    public boolean getIsDone() {
        return isDone;
    }
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }
    public void markAsDone() {
        this.isDone = true;
    }
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    protected String getFormattedDate() {
        return hasDate ? date.format(DateTimeFormatter.ofPattern("MMM d yyyy")) : "";
    }
    protected String getFormattedTime() {
        return hasTime ? time.format(DateTimeFormatter.ofPattern("hh:mm:ss")) : "";
    }
    private void setDate(LocalDate date) {
        this.date = date;
        hasDate = true;
    }
    private void setTime(LocalTime time) {
        this.time = time;
        hasTime = true;
    }
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
