import command.Command;
import command.CommandExecute;
import exception.DukeException;
import exception.ExceptionType;


import tasks.Task;
import tasks.TaskManager;

import java.util.Scanner;

public class Duke {

    public static final String EXIT_MESSAGE = "Bye! Hope to see you again soon!";
    public static final String LINE_SEPARATOR = "\t"+"-".repeat(60);

    public static void printGreeting() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("\t" + "Hello! I'm Duke \n    What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    public static void main(String[] args) {
        printGreeting();
        TaskManager taskList = new TaskManager();

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
                    taskList.listTasks();
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
                    exit(taskList);
                    break;
                case DELETE:
                    int id = Integer.parseInt(input.getMessage().substring(7));
                    removeTask(taskList, id);
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

    public static void exit(TaskManager taskList) {
        System.out.println("\t" + EXIT_MESSAGE);
        System.out.println("\tYou currently have " + taskList.getTaskLeft() + " tasks left");
        System.out.println("\t" + "Happy to help you organize work. Anywhere, anytime!");
        System.out.println(LINE_SEPARATOR);
    }

    private static void removeTask(TaskManager taskList, int id){
        Task task = taskList.deleteTask(id);
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t\t" + task.toString());
        System.out.println("\t" + "Now you have " + taskList.getTaskCount() + " tasks in your list :)");
    }

    private static void printTaskAddedMessage(Task task, int taskCount) {
        System.out.println("\t" + "Got it. I've added this to your custom-list: ");
        System.out.println("\t\t" + task.toString());
        System.out.println("\t" + "Now you have " + taskCount + " tasks in your list :)");
    }

    private static void addEvent(TaskManager taskList, String eventTask) throws DukeException {
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

        Task task = taskList.addEvent(description, by);
        printTaskAddedMessage(task, taskList.getTaskCount());
    }

    private static void addDeadline(TaskManager taskList, String deadlineTask) throws DukeException {

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
        Task task = taskList.addDeadline(description, by);
        printTaskAddedMessage(task, taskList.getTaskCount());
    }


    private static void addToDo(TaskManager taskList, String toDoTask) throws DukeException {
        try {
            String taskDetails = toDoTask.substring(5).trim();
            Task task;
            task = taskList.addToDO(taskDetails);
            printTaskAddedMessage(task, taskList.getTaskCount());
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
        }
    }
    //comment
    private static void taskDone(TaskManager taskList, int taskNumber) throws DukeException {

        if (taskNumber <= 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if (taskNumber > Task.getTaskCount()) {
            throw new DukeException(ExceptionType.INVALID_NUMBER);
        } else {
            Task task = taskList.markAsDone(taskNumber);
            printTaskAddedMessage(task, taskList.getTaskCount());
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
