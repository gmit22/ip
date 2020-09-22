import command.Command;
import command.CommandExecute;
import exception.DukeException;
import exception.ExceptionType;
import file.FileManager;
import tasks.Task;
import tasks.TaskManager;
import ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Duke {


    public static final String TASK_AT = "/at";
    public static final String TASK_BY = "/by";

    /*path for home directory*/
    private static final String ROOT = System.getProperty("user.dir");

    private static final Path FILE_PATH = Paths.get(ROOT, "src", "main", "data", "data.txt");
    private static final Path DIR_PATH = Paths.get(ROOT, "src", "main", "data");
    private static final boolean directoryExists = Files.exists(DIR_PATH);

    private static final Ui ui = new Ui();
    private static TaskManager taskList;

    public Duke() {

        FileManager fileManager;
        if (!directoryExists) {
            File file = new File(String.valueOf(DIR_PATH));
            file.mkdir();
        }
        fileManager = new FileManager(FILE_PATH.toString());

        // Create TaskManager
        try {
            taskList = createTaskManager(fileManager);
        } catch (DukeException e) {
            ui.printError(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Ui.printGreeting();

        Scanner in = new Scanner(System.in);
        Command input = new Command(in.nextLine());

        //loop runs till the user inputs bye in the cli
        while (true) {
            try {
                CommandExecute type = input.extractType();
                Ui.printLineSeparator();
                switch (type) {
                case MARK_DONE:
                    int taskNumber = extractTaskNumber(input.getMessage());
                    taskDone(taskList, taskNumber);
                    break;
                case LIST:
                    //print list of items
                    TaskManager.listTasks();
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
                    Ui.exit(taskList);
                    break;
                case DELETE:
                    int id = Integer.parseInt(input.getMessage().substring(7));
                    Ui.removeTask(taskList, id);
                    break;
                default:
                    Ui.printCommandNotFound();
                    break;
                }
                if (type == CommandExecute.EXIT) {
                    break;
                }
            } catch (DukeException e) {
                Ui.printError(e.toString());
            }
            try {
                taskList.writeToFile();
            } catch (IOException e) {
                Ui.printFileError();
            }
            Ui.printLineSeparator();
            input = new Command(in.nextLine());
        }
    }

    private static void addEvent(TaskManager taskList, String eventTask) throws DukeException {
        String[] taskDetails = eventTask.trim().split(TASK_AT);
        if (taskDetails[0].equals("event")) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
        if (!eventTask.contains(TASK_AT)) {
            throw new DukeException(ExceptionType.MISSING_IDENTIFIER);
        }
        if (taskDetails.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_TIME);
        }
        String description = taskDetails[0].substring(6).trim();
        String by = taskDetails[1].trim();
        Task task = TaskManager.addEvent(description, by);
        Ui.printTaskAddedMessage(task, taskList.getTaskCount());
    }

    private static void addDeadline(TaskManager taskList, String deadlineTask) throws DukeException {
        String[] taskDetails = deadlineTask.trim().split(TASK_BY);
        if (!deadlineTask.contains(TASK_BY)) {
            throw new DukeException(ExceptionType.MISSING_IDENTIFIER);
        }
        if (taskDetails[0].equals("deadline")) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
        if (taskDetails.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_TIME);
        }
        String description = taskDetails[0].substring(9).trim();
        String by = taskDetails[1].trim();
        Task task = TaskManager.addDeadline(description, by);
        Ui.printTaskAddedMessage(task, taskList.getTaskCount());
    }


    private static void addToDo(TaskManager taskList, String toDoTask) throws DukeException {
        try {
            String taskDetails = toDoTask.substring(5).trim();
            Task task;
            task = TaskManager.addToDo(taskDetails);
            Ui.printTaskAddedMessage(task, taskList.getTaskCount());
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
    }

    private static void taskDone(TaskManager taskList, int taskNumber) throws DukeException {

        if (taskNumber <= 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if (taskNumber > taskList.getTaskCount()) {
            throw new DukeException(ExceptionType.INVALID_NUMBER);
        } else {
            Task task = TaskManager.markAsDone(taskNumber);
            Ui.printTaskDone(task, taskList.getTaskCount());
        }
    }

    private static TaskManager createTaskManager(FileManager fileManager) throws DukeException, IOException {
        // Will loop as long as FileNotFoundException is caught, and file is not created
        while (true) {
            try {
                return new TaskManager(fileManager);
            } catch (FileNotFoundException e) {
                // Create file if not found
                fileManager.createFile();
            }
        }
    }

    private static int extractTaskNumber(String command) {
        int itemNo;
        try {
            String itemNumber = command.trim().substring(5, command.length()).trim();
            itemNo = Integer.parseInt(itemNumber);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            itemNo = 0;
        }
        return itemNo;
    }
}