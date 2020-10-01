import command.CommandExecute;
import command.Parser;
import exception.DukeException;
import file.FileManager;
import tasks.TaskManager;

import java.io.File;
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

    private static final Path FILE_PATH = Paths.get(ROOT, "data", "data.txt");
    private static final Path DIR_PATH = Paths.get(ROOT, "data");
    private static final boolean DIRECTORY_EXISTS = Files.exists(DIR_PATH);

    private static final Ui ui = new Ui();
    private static TaskManager taskList;

    public Duke() {

        FileManager fileManager;
        File file = new File(String.valueOf(DIR_PATH));

        if (!DIRECTORY_EXISTS) {
            file.mkdirs();
        }
        fileManager = new FileManager(FILE_PATH.toString());
        // Create TaskManager
        try {
            taskList = TaskManager.createTaskManager(fileManager);
        } catch (IOException e) {
            ui.printError(e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Duke().run();
    }

    /**
     * Runs the task scheduler.
     * Manages the messages shown to the user as the output.
     * Handles commands, UI and storing of tasks to a .txt file.
     */
    public void run() {
        ui.printGreeting();
        String[] taskDetails;
        Scanner in = new Scanner(System.in);
        Parser input = new Parser(in.nextLine());

        //loop runs till the user inputs bye in the cli
        while (true) {
            try {
                CommandExecute type = input.extractType();
                ui.printLineSeparator();
                switch (type) {
                case MARK_DONE:
                    ui.printTaskDone(TaskManager.taskDone(input.extractTaskNumber()));
                    break;
                case LIST:
                    //print list of items
                    ui.listTasks(taskList);
                    break;
                case TODO:
                    ui.printTaskAddedMessage(TaskManager.addToDo(input.getMessage()), taskList.getTaskCount());
                    break;
                case DEADLINE:
                    String deadlineTask = input.getMessage().substring(9).trim();
                    taskDetails = deadlineTask.trim().split(TASK_BY);
                    ui.printTaskAddedMessage(TaskManager.addDeadline(taskDetails), taskList.getTaskCount());
                    break;
                case EVENT:
                    String eventTask = input.getMessage().substring(6);
                    taskDetails = eventTask.trim().split(TASK_AT);
                    ui.printTaskAddedMessage(TaskManager.addEvent(taskDetails), taskList.getTaskCount());
                    break;
                case EXIT:
                    ui.exit(taskList);
                    break;
                case DELETE:
                    ui.removeTask(TaskManager.deleteTask(input.getMessage()), taskList.getTaskCount());
                    break;
                case FIND:
                    ui.printFoundTasks(TaskManager.findTask(input.getMessage()));
                    break;
                default:
                    ui.printCommandNotFound();
                    break;
                }
                if (type == CommandExecute.EXIT) {
                    break;
                }
            } catch (DukeException e) {
                ui.printError(e.toString());
            } catch (StringIndexOutOfBoundsException e) {
                ui.printError("\t Index out of bound. Please check your input format.");
            }
            try {
                taskList.writeToFile();
            } catch (IOException e) {
                ui.printFileError();
            }
            ui.printLineSeparator();
            input = new Parser(in.nextLine());
        }
    }
}