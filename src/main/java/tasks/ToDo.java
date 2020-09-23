package tasks;

public class ToDo extends Task {
    /**
     * Constructor.
     * @param description Description of toDoTask.
     */
    public ToDo(String description) {
        super(description);
    }
    /**
     * @return String representation of toDoTask.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
