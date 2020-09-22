package tasks;

public class Event extends Task {
    String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
        setDateTime(at);
    }
    @Override
    public String toString() {
        String dateFormatted = "";
        if (hasDate && hasTime) {
            dateFormatted = getFormattedDate() + " " + getFormattedTime();
        } else {
            dateFormatted = at;
        }
        return "[E]" + super.toString() + " (at: " + dateFormatted + ")";
    }
    public String getAt() {
        return at;
    }

}
