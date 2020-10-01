import tasks.Task;
import tasks.TaskManager;

import java.util.ArrayList;

public class Ui {
    /**
     * Constants used to display output to the user.
     * Changing the LEN_LINE_SEPARATOR can change the length of the line separator shown.
     */
    public static final String EXIT_MESSAGE = "Bye! Dobby is now free!";
    private static final int LEN_LINE_SEPARATOR = 60;
    public static final String LINE_SEPARATOR = "\t" + "-".repeat(LEN_LINE_SEPARATOR);
    /**
     *Outputs confirmation message for a task marked to be done.
     *Displays the task in consideration for this.
     * @param task, task object that is marked done in the taskList.
     */
    public void printTaskDone(Task task) {
        System.out.println("\t" + "Got it. I've marked this task as done: ");
        System.out.println("\t\t" + task.toString());
    }
    /**
     * @param taskList ArrayList object of taskManager containing tasks added (/data.txt).
     * Prints the list of all tasks stored.
     */
    public void listTasks(TaskManager taskList) {
        if (taskList.getTaskCount() == 0) {
            System.out.println("\t" + "You currently have no tasks");
            System.out.println("\t" + "To update your to-do list, just type the task");
        } else {
            System.out.println("\t" + "Here are the tasks in your list:");
            for (int j = 0; j < taskList.getTaskCount(); j++) {
                System.out.println("\t" + (j + 1) + ". " + taskList.getTask(j).toString());
            }
        }
    }
    /**
     * Prints confirmation message for added task.
     * @param task Task that is added to the taskManager.
     * @param taskCount Number of total tasks added to the taskManager.
     */
    public void printTaskAddedMessage(Task task, int taskCount) {
        System.out.println("\t" + "Got it. I've added this to your custom-list: ");
        System.out.println("\t\t" + task.toString());
        System.out.println("\t" + "Now you have " + taskCount + " tasks in your list :)");
    }
    /**
     * Removes a task from the taskManager.
     * @param task Task that has been deleted, valid index needs to be inputted by the user.
     * @param taskCount Total number of tasks, after task has been removed.
     */
    public void removeTask(Task task, int taskCount) {
        if (task == null) {
            printError("Please enter a valid index for task deletion.");
        } else {
            System.out.println("\tNoted. I've removed this task:");
            System.out.println("\t\t" + task.toString());
            System.out.println("\t" + "Now you have " + taskCount + " tasks in your list :)");
        }
    }
    /**
     * Exit message, shows number of tasks not marked as done
     * @param taskList ArrayList object of taskManager containing tasks added.
     */
    public void exit(TaskManager taskList) {
        System.out.println("\t" + EXIT_MESSAGE);
        System.out.println("\tYou currently have " + taskList.getTaskLeft() + " tasks left");
        System.out.println("\t" + "Happy to help you organize work. Anywhere, anytime!");
        System.out.println(LINE_SEPARATOR);
    }
    /**
     * Welcome message to the user.
     */
    public void printGreeting() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("\t" + "Hello Master! I'm Dobby \n\tWhat can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }
    /**
     * Display error message passed by string.
     * @param s Error message
     */
    public void printError(String s) {
        System.out.println(s);
    }
    /**
     * Prints error if any, during writing to the .txt file.
     */
    public void printFileError() {
        System.out.println(" Error in reading/writing the file");
    }
    /**
     * Notifies user if invalid command is entered.
     */
    public void printCommandNotFound() {
        System.out.println("It seems like you entered an unidentified command :(");
    }
    /**
     * Line separator for marker between user inputs.
     */
    public void printLineSeparator() {
        System.out.println(LINE_SEPARATOR);
    }
    /**
     * Outputs tasks containing the query keyword.
     * @param foundTasks ArrayList of tasks that match the keyWord
     */
    public void printFoundTasks(ArrayList<Task> foundTasks) {
        if (foundTasks.size() == 0) {
            System.out.println("\tNo matching tasks found, please check your keyword.");
        } else {
            System.out.println("\tHere are matching tasks in your list:");
            for (int i = 0; i < foundTasks.size(); i++) {
                System.out.println("\t\t" + (i + 1) + ". " + foundTasks.get(i).toString());
            }
        }
    }
}
