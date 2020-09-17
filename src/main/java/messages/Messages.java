package messages;

import tasks.Task;
import tasks.TaskManager;

public class Messages {
    public static final String EXIT_MESSAGE = "Bye! Dobby is now free!";
    private static final int LEN_LINE_SEPARATOR = 60;
    public static final String LINE_SEPARATOR = "\t" + "-".repeat(LEN_LINE_SEPARATOR);

    public static void printTaskAddedMessage(Task task, int taskCount) {
        System.out.println("\t" + "Got it. I've added this to your custom-list: ");
        System.out.println("\t\t" + task.toString());
        System.out.println("\t" + "Now you have " + taskCount + " tasks in your list :)");
    }

    public static void printTaskDone(Task task, int taskCount) {
        System.out.println("\t" + "Got it. I've marked this task as done: ");
        System.out.println("\t\t" + task.toString());
    }

    public static void removeTask(TaskManager taskList, int id) {
        Task task = TaskManager.deleteTask(id);
        System.out.println("\tNoted. I've removed this task:");
        System.out.println("\t\t" + task.toString());
        System.out.println("\t" + "Now you have " + taskList.getTaskCount() + " tasks in your list :)");
    }

    public static void exit(TaskManager taskList) {
        System.out.println("\t" + EXIT_MESSAGE);
        System.out.println("\tYou currently have " + taskList.getTaskLeft() + " tasks left");
        System.out.println("\t" + "Happy to help you organize work. Anywhere, anytime!");
        System.out.println(LINE_SEPARATOR);
    }
    public static void printGreeting() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("\t" + "Hello Master! I'm Dobby \n\tWhat can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    public static void printError(String s){
        System.out.println(s);
    }

    public static void printFileError() {
        System.out.println(" Error in reading/writing the file");
    }

    public static void printCommandNotFound() {
        System.out.println("It seems like you entered an unidentified command :(");
    }
}
