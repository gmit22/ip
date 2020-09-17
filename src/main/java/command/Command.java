package command;

import exception.DukeException;
import exception.ExceptionType;

public class Command {

    private String message;

    public Command(String message) {
        this.message = message;
    }

    public CommandExecute extractType() throws DukeException {
        message = message.trim();
        String command = message;
        if (message.contains(" ")) {
            command = message.split(" ")[0];
        }
        if (command.equalsIgnoreCase("bye")) {
            return CommandExecute.EXIT;
        } else if (command.equalsIgnoreCase("list")) {
            return CommandExecute.LIST;
        } else if (command.equalsIgnoreCase("done")) {
            return CommandExecute.MARK_DONE;
        } else if (message.length() < 4) {
            throw new DukeException(ExceptionType.UNIDENTIFIED);
        } else if (command.equalsIgnoreCase("todo")) {
            return CommandExecute.TODO;
        } else if (command.equalsIgnoreCase("deadline")) {
            return CommandExecute.DEADLINE;
        } else if (command.equalsIgnoreCase("event")) {
            return CommandExecute.EVENT;
        } else if (command.equalsIgnoreCase("delete")) {
            return CommandExecute.DELETE;
        } else {
            throw new DukeException(ExceptionType.UNIDENTIFIED);
        }
    }
    public String getMessage() {
        return message;
    }
}