package tasks;

public class Event extends Task {
    String at;

    /**
     * Constructor
     *
     * @param description Description of the eventTask.
     * @param at          Location, timing/date of the eventTask.
     */
    public Event(String description, String at) {
        super(description);
        this.at = at;
        setDateTime(at);
    }

    /**
     * @return String representation of eventTask.
     */
    @Override
    public String toString() {
        String dateFormatted;
        if (hasDate && hasTime) {
            dateFormatted = getFormattedDate() + " " + getFormattedTime();
        } else {
            dateFormatted = at;
        }
        return "[E]" + super.toString() + " (at: " + dateFormatted + ")";
    }

    /**
     * @return String parameter details of the object.
     */
    public String getAt() {
        return at;
    }

}
