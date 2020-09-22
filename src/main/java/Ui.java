import tasks.Task;
import tasks.TaskManager;

import java.util.ArrayList;

public class Ui {
    public static final String EXIT_MESSAGE = "Bye! Dobby is now free!";
    private static final int LEN_LINE_SEPARATOR = 60;
    public static final String LINE_SEPARATOR = "\t" + "-".repeat(LEN_LINE_SEPARATOR);

    public static void printTaskDone(Task task) {
        System.out.println("\t" + "Got it. I've marked this task as done: ");
        System.out.println("\t\t" + task.toString());
    }
    public static void listTasks(TaskManager taskList) {
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
    public void printTaskAddedMessage(Task task, int taskCount) {
        System.out.println("\t" + "Got it. I've added this to your custom-list: ");
        System.out.println("\t\t" + task.toString());
        System.out.println("\t" + "Now you have " + taskCount + " tasks in your list :)");
    }
    public void removeTask(Task task, int taskCount) {
        if (task == null) {
            printError("Please enter a valid index for task deletion.");
        } else {
            System.out.println("\tNoted. I've removed this task:");
            System.out.println("\t\t" + task.toString());
            System.out.println("\t" + "Now you have " + taskCount + " tasks in your list :)");
        }
    }
    public void exit(TaskManager taskList) {
        System.out.println("\t" + EXIT_MESSAGE);
        System.out.println("\tYou currently have " + taskList.getTaskLeft() + " tasks left");
        System.out.println("\t" + "Happy to help you organize work. Anywhere, anytime!");
        System.out.println(LINE_SEPARATOR);
    }
    public void printGreeting() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("\t" + "Hello Master! I'm Dobby \n\tWhat can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }
    public void printError(String s) {
        System.out.println(s);
    }
    public void printFileError() {
        System.out.println(" Error in reading/writing the file");
    }
    public void printCommandNotFound() {
        System.out.println("It seems like you entered an unidentified command :(");
    }
    public void printLineSeparator() {
        System.out.println(LINE_SEPARATOR);
    }
    public void printFoundTasks(ArrayList<Task> foundTasks, TaskManager taskList) {
        if (foundTasks.size() == 0) {
            System.out.println("No matching tasks found, please check your keyword.");
        } else {
            System.out.println("Here are matching tasks in your list:");
            for (int i = 0; i < foundTasks.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + foundTasks.get(i).toString());
            }
        }
    }
}
