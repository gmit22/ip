package exception;

public class DukeException extends Exception {

    private ExceptionType exception;

    /**
     * Constructor.
     *
     * @param exception Store the exception in the object.
     */
    public DukeException(ExceptionType exception) {
        this.exception = exception;
    }

    /**
     * @return String message of the error thrown.
     */
    @Override
    public String toString() {
        return (exception.getMessage());
    }
}