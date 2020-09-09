package Task;

public class Task {
    protected static int taskCount = 0;
    protected static int taskLeft = 0;
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        taskCount++;
        taskLeft++;
    }
    public static int getTaskCount() {
        return taskCount;
    }
    public static void markTaskCompleted() {
        taskLeft--;
    }
    public static int getTaskLeft() {
        return taskLeft;
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
}
