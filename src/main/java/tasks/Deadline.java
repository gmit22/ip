package tasks;

public class Deadline extends Task {
    String by;
    /**
     * Constructor
     * @param description Description of the task.
     * @param by Deadline for the task.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        setDateTime(by);
    }
    /**
     * @return String representation of deadlineTask.
     */
    @Override
    public String toString() {
        String dateFormatted = "";
        if (hasDate && hasTime){
            dateFormatted = getFormattedDate() + " " + getFormattedTime();
        }else{
            dateFormatted = by;
        }
        return "[D]" + super.toString() + " (by: " + dateFormatted + ")";
    }
    /**
     * @return String parameter details of the object.
     */
    public String getBy(){
        return by;
    }
}
