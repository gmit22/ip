import command.Parser;
import command.CommandExecute;
import exception.DukeException;
import file.FileManager;
import tasks.Task;
import tasks.TaskManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
            taskList = createTaskManager(fileManager);
        } catch (IOException e) {
            ui.printError(e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Duke().run();
    }
    /**
     * Creates a data.txt file if not existing.
     * Creates a TaskManager object to update and retrieve data from the .txt file.
     **/
    private static TaskManager createTaskManager(FileManager fileManager) throws IOException {
        // Will loop as long as FileNotFoundException is caught, and file is not created
        while (true) {
            try {
                return new TaskManager(fileManager);
            } catch (FileNotFoundException | DukeException e) {
                // Create file if not found
                try {
                    fileManager.createFile();
                } catch (IOException err) {
                    throw err;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
    /**
     * Runs the task scheduler.
     * Manages the messages shown to the user as the output.
     * Handles commands, UI and storing of tasks to a .txt file.
     */
    public void run() {
        ui.printGreeting();
        Task task;
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
                    int taskNumber = input.extractTaskNumber();
                    task = TaskManager.taskDone(taskNumber);
                    ui.printTaskDone(task);
                    break;
                case LIST:
                    //print list of items
                    ui.listTasks(taskList);
                    break;
                case TODO:
                    String toDoTask = input.getMessage().substring(5).trim();
                    task = TaskManager.addToDo(toDoTask);
                    ui.printTaskAddedMessage(task, taskList.getTaskCount());
                    break;
                case DEADLINE:
                    String deadlineTask = input.getMessage().substring(9).trim();
                    taskDetails = deadlineTask.trim().split(TASK_BY);
                    task = TaskManager.addDeadline(taskDetails);
                    ui.printTaskAddedMessage(task, taskList.getTaskCount());
                    break;
                case EVENT:
                    String eventTask = input.getMessage().substring(6);
                    taskDetails = eventTask.trim().split(TASK_AT);
                    task = TaskManager.addEvent(taskDetails);
                    ui.printTaskAddedMessage(task, taskList.getTaskCount());
                    break;
                case EXIT:
                    ui.exit(taskList);
                    break;
                case DELETE:
                    int id = Integer.parseInt(input.getMessage().substring(7));
                    task = TaskManager.deleteTask(id);
                    ui.removeTask(task, taskList.getTaskCount());
                    break;
                case FIND:
                    String keyWord = input.getMessage().substring(5).trim();
                    ArrayList<Task> foundTasks = TaskManager.findTask(keyWord);
                    ui.printFoundTasks(foundTasks);
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