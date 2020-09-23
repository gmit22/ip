package exception;

/**
 * Store messages as constant to be printed, in case of exceptions relevant to the project thrown.
 */
public enum ExceptionType {
    MISSING_TASK_DESCRIPTION("\tPlease add the missing task description"),
    MISSING_IDENTIFIER("\tIdentifier (/at or /by) missing."),
    UNIDENTIFIED("\tUnknown command entered"),
    NOT_A_NUMBER("\tPlease enter a valid task number"),
    MISSING_ON_TIME("\tYou did not enter  the time for the task"),
    INVALID_NUMBER("\tThat's not a valid item number for any task in your list!");

    private String message;
    /**
     * Constructor.
     * @param message
     */
    ExceptionType(String message) {
        this.message = message;
    }
    /**
     * @return String type message relevant to the exception.
     */
    public String getMessage() {
        return message;
    }
}