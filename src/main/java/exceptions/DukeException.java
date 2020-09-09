package exceptions;

public abstract class DukeException extends Exception {

    public DukeException() {
        super();
    }
    public abstract void printErrorMessage(String command);
}
