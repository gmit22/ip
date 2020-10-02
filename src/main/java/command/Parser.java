package command;

import exception.DukeException;
import exception.ExceptionType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Parser {

    private String message;

    /**
     * Constructor.
     *
     * @param message Command inputted by the user.
     */
    public Parser(String message) {
        this.message = message;
    }

    /**
     * @param line Takes in the deadline by/at part of input.
     * @return Parses line to return datetime object.
     */
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

    /**
     * @param line Deadline by/at part of user input.
     * @return Parses line to return LocalTime object.
     */
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

    /**
     * Uses enum CommandExecute class to decide type of command.
     *
     * @return Type of command to process, call relevant functions.
     * @throws DukeException In case of unidentified commands.
     */
    public CommandExtract extractType() throws DukeException {
        message = message.trim();
        String command = message;
        if (message.contains(" ")) {
            command = message.split(" ")[0];
        }
        if (command.equalsIgnoreCase("bye")) {
            return CommandExtract.EXIT;
        } else if (command.equalsIgnoreCase("list")) {
            return CommandExtract.LIST;
        } else if (command.equalsIgnoreCase("done")) {
            return CommandExtract.MARK_DONE;
        } else if (message.length() < 4) {
            throw new DukeException(ExceptionType.UNIDENTIFIED);
        } else if (command.equalsIgnoreCase("todo")) {
            return CommandExtract.TODO;
        } else if (command.equalsIgnoreCase("deadline")) {
            return CommandExtract.DEADLINE;
        } else if (command.equalsIgnoreCase("event")) {
            return CommandExtract.EVENT;
        } else if (command.equalsIgnoreCase("delete")) {
            return CommandExtract.DELETE;
        } else if (command.equalsIgnoreCase("find")) {
            return CommandExtract.FIND;
        } else {
            throw new DukeException(ExceptionType.UNIDENTIFIED);
        }
    }

    /**
     * @return String message of command.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Used for parsing taskDone command to get task to be done.
     *
     * @return Integer taskId of the task.
     */
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