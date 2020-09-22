package tasks;

public class Deadline extends Task {
    String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        setDateTime(by);
    }
    public String getBy(){
        return by;
    }
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

}
