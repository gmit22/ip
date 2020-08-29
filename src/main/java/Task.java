public class Task {
    protected String description;
    protected boolean isDone;
    protected static int taskCount = 0;
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        taskCount++;
    }
    public static int getTaskCount(){
        return taskCount;
    }
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public void markAsDone() {
        this.isDone = true;
    }
}
