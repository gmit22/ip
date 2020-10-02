import command.CommandExtract;
import command.ExitCommand;
import command.Parser;
import exception.DukeException;
import file.Storage;
import tasks.TaskManager;
import ui.Ui;

import java.io.IOException;
import java.util.Scanner;

public class Duke {
    public static final String TASK_AT = "/at";
    public static final String TASK_BY = "/by";
    private static final Ui ui = new Ui();
    private static TaskManager taskList;

    public Duke() {
        // Create TaskManager
        try {
            taskList = TaskManager.createTaskManager(Storage.storage(ui));
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
        Scanner in = new Scanner(System.in);
        Parser input = new Parser(in.nextLine());

        //loop runs till the user inputs bye in the cli
        while (true) {
            try {
                CommandExtract type = input.extractType();
                ui.printLineSeparator();
                switch (type) {
                case MARK_DONE:
                    ui.printTaskDone(command.DoneCommand.taskDone(taskList, input.extractTaskNumber()));
                    break;
                case LIST:
                    //print list of items
                    ui.listTasks(taskList);
                    break;
                case TODO:
                    ui.printTaskAddedMessage(command.AddToDo.addToDo(taskList, input.getMessage()), taskList.getTaskCount());
                    break;
                case DEADLINE:
                    String deadlineTask = input.getMessage().substring(9).trim();
                    ui.printTaskAddedMessage(command.AddDeadline.addDeadline(taskList, deadlineTask.trim().split(TASK_BY)), taskList.getTaskCount());
                    break;
                case EVENT:
                    String eventTask = input.getMessage().substring(6);
                    ui.printTaskAddedMessage(command.AddEvent.addEvent(taskList, eventTask.trim().split(TASK_AT)), taskList.getTaskCount());
                    break;
                case EXIT:
                    break;
                case DELETE:
                    ui.removeTask(command.DeleteCommand.deleteTask(taskList, input.getMessage()), taskList.getTaskCount());
                    break;
                case FIND:
                    ui.printFoundTasks(command.FindCommand.findTask(taskList, input.getMessage()));
                    break;
                default:
                    ui.printCommandNotFound();
                    break;
                }
                if (type == CommandExtract.EXIT) {
                    ExitCommand.exit(taskList);
                    ui.exit(taskList);
                    break;
                }
            } catch (DukeException e) {
                ui.printError(e.toString());
            } catch (StringIndexOutOfBoundsException e) {
                ui.printError("\t Index out of bound. Please check your input format.");
            }
            ui.printLineSeparator();
            input = new Parser(in.nextLine());
        }
    }
}