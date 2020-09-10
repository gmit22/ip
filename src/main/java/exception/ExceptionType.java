package exception;


public enum ExceptionType {
    MISSING_DESCRIPTION("\tPlease add the missing task description"),
    MISSING_IDENTIFIER("\tIdentifier (/at or /by) missing."),
    UNIDENTIFIED("\tUnknown command entered"),
    NOT_A_NUMBER("\tPlease enter a valid task number"),
    MISSING_ON_TIME("\tOh, you forgot to add the time of the task"),
    INVALID_NUMBER("\tThat's not a valid item number for any task in your list!");


    private String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}