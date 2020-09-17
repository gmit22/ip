package tasks;

public abstract class Task {
    protected static int taskCount = 0;
    protected static int taskLeft = 0;
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    public String getDescription() {
        return description;
    }
    public boolean getIsDone(){
        return isDone;
    }
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }
    public void markAsDone() {
        this.isDone = true;
        taskLeft--;
    }
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
