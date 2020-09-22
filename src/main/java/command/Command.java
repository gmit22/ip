package command;

import exception.DukeException;
import exception.ExceptionType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Command {

    private String message;

    public Command(String message) {
        this.message = message;
    }

    public static LocalDate parseDate(String line) {
        String[] params = line.split("\\s+");
        LocalDate date = null;
        for (String d : params) {
            try {
                date = LocalDate.parse(d);
            } catch (DateTimeParseException e) {
            }
        }
        return date;
    }
    public static LocalTime parseTime(String line) {
        String[] params = line.split("\\s+");
        LocalTime time = null;
        for (String d : params) {
            try {
                time = LocalTime.parse(d);
            } catch (DateTimeParseException e) {
            }
        }
        return time;
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
        } else if (command.equalsIgnoreCase("find")) {
            return CommandExecute.FIND;
        } else {
            throw new DukeException(ExceptionType.UNIDENTIFIED);
        }
    }
    public String getMessage() {
        return message;
    }
    public int extractTaskNumber() {
        int itemNo;
        try {
            String itemNumber = message.trim().substring(5, message.length()).trim();
            itemNo = Integer.parseInt(itemNumber);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            itemNo = 0;
        }
        return itemNo;
    }
}