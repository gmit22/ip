import command.Command;
import command.CommandExecute;
import exception.DukeException;
import exception.ExceptionType;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.ToDo;

import java.util.Scanner;

public class Duke {

    public static final String EXIT_MESSAGE = "Bye! Hope to see you again soon!";
    public static final String LINE_SEPARATOR = "-".repeat(60);

    public static void printGreeting() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("\t" + "Hello! I'm Duke \n    What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    public static void main(String[] args) {
        printGreeting();
        Task[] taskList = new Task[100];

        Scanner in = new Scanner(System.in);
        Command input = new Command(in.nextLine());

        //loop runs till the user inputs bye in the cli
        while (true) {
            try {
                CommandExecute type = input.extractType();
                System.out.println(LINE_SEPARATOR);
                switch (type) {
                case MARK_DONE:
                    int taskNumber = extractTaskNumber(input.getMessage());
                    taskDone(taskList, taskNumber);
                    break;
                case LIST:
                    //print list of items
                    listTasks(taskList);
                    break;
                case TODO:
                    String toDoTask = input.getMessage();
                    addToDo(taskList, toDoTask);
                    break;
                case DEADLINE:
                    String deadlineTask = input.getMessage();
                    addDeadline(taskList, deadlineTask);
                    break;
                case EVENT:
                    String eventTask = input.getMessage();
                    addEvent(taskList, eventTask);
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    System.out.println(" ☹ OOPS!!! It seems that you have entered a wrong command :-(!");
                    break;
                }
                if (type == CommandExecute.EXIT) {
                    break;
                }
            } catch (DukeException e) {
                System.out.println(e.toString());
            }
            System.out.println(LINE_SEPARATOR);
            input = new Command(in.nextLine());
        }
    }

    public static void exit() {
        System.out.println("\t" + EXIT_MESSAGE);
        System.out.println("\tYou currently have " + Task.getTaskLeft() + " tasks left");
        System.out.println("\t" + "Happy to help you organize work. Anywhere, anytime!");
        System.out.println(LINE_SEPARATOR);
    }

    private static void addEvent(Task[] taskList, String eventTask) throws DukeException {

        String[] taskDetails = eventTask.trim().split("/at");
        if (taskDetails[0].equals("deadline")) {
            throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
        }
        if (!eventTask.contains("/at")) {
            throw new DukeException(ExceptionType.MISSING_IDENTIFIER);
        }
        if (taskDetails.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_TIME);
        }
        String description = taskDetails[0].substring(6).trim();
        String by = taskDetails[1].trim();
        Event newItem = new Event(description, by);
        taskList[Task.getTaskCount() - 1] = newItem;
        System.out.println("\t" + "Got it. I've added this to your custom-list: ");
        System.out.println("\t\t" + taskList[Task.getTaskCount() - 1].toString());
        System.out.println("\t" + "Now you have " + Task.getTaskCount() + " tasks in your list :)");
    }

    private static void addDeadline(Task[] taskList, String deadlineTask) throws DukeException {

        String[] taskDetails = deadlineTask.trim().split("/by");
        if (!deadlineTask.contains("/by")) {
            throw new DukeException(ExceptionType.MISSING_IDENTIFIER);
        }
        if (taskDetails[0].equals("deadline")) {
            throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
        }
        if (taskDetails.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_TIME);
        }
        String description = taskDetails[0].substring(9).trim();
        String by = taskDetails[1].trim();
        Deadline newItem = new Deadline(description, by);
        taskList[Task.getTaskCount() - 1] = newItem;
        System.out.println("\t" + "Got it. I've added this to your custom-list: ");
        System.out.println("\t\t" + taskList[Task.getTaskCount() - 1].toString());
        System.out.println("\t" + "Now you have " + Task.getTaskCount() + " tasks in your list :)");
    }

    private static void addToDo(Task[] taskList, String toDoTask) throws DukeException {
        try {
            String taskDetails = toDoTask.substring(5).trim();
            ToDo newItem = new ToDo(taskDetails);
            taskList[Task.getTaskCount() - 1] = newItem;
            System.out.println("\t Got it. I've added this to your custom-list: ");
            System.out.println("\t\t" + taskList[Task.getTaskCount() - 1].toString());
            System.out.println("\t Now you have " + Task.getTaskCount() + " tasks in your list :)");
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
        }
    }
    //comment
    private static void taskDone(Task[] taskList, int taskNumber) throws DukeException {

        if (taskNumber <= 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if (taskNumber > Task.getTaskCount()) {
            throw new DukeException(ExceptionType.INVALID_NUMBER);
        } else {
            taskList[taskNumber - 1].markAsDone();
            System.out.println("\t" + "Nice! I've marked this task as done:");
            System.out.println("\t\t" + taskList[taskNumber - 1].toString());
            System.out.println("\t" + "Type \"list\" to see a list of pending tasks");
        }
    }

    private static void listTasks(Task[] taskList) {
        if (Task.getTaskCount() == 0) {
            System.out.println("\t" + "You currently have no tasks");
            System.out.println("\t" + "To update your to-do list, just type the task");
        } else {
            System.out.println("\t" + "Here are the tasks in your list:");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("\t" + (j + 1) + ". " + taskList[j].toString());
            }
        }
    }

    private static int extractTaskNumber(String command) {
        int itemNo;
        try {
            String itemNumber = command.trim().substring(5, command.length()).trim();
            itemNo = Integer.parseInt(itemNumber);
        } catch (NumberFormatException e) {
            itemNo = 0;
        } catch (StringIndexOutOfBoundsException e) {
            itemNo = 0;
        }
        return itemNo;
    }
}
