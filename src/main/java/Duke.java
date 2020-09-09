import Task.Task;
import Task.Deadline;
import Task.Event;
import Task.ToDo;

import java.util.Scanner;

public class Duke {

    public static void printLine(int n) {
        System.out.println("\t" + "-".repeat(n));
    }

    public static void printGreeting() {
        printLine(60);
        System.out.println("\t" + "Hello! I'm Duke \n    What can I do for you?");
        printLine(60);
    }

    public static void main(String[] args) {
        printGreeting();
        Task[] tsk = new Task[100];
        Scanner in = new Scanner(System.in);
        String input;
        input = in.nextLine();
        String command;
//        System.out.println(Task.Task.getTaskCount());
        int spacePos;
        String eventDone;

        //loop runs till the user inputs bye in the cli
        while (!input.equals("bye")) {
            //this is to see the command details after the type of event is mentioned
            spacePos = input.indexOf(" ");
            //Type of task to be added, stored in command
            command = spacePos > 0 ? input.substring(0, spacePos) : input;
            printLine(60);
            switch (command) {
            case "done":
                eventDone = input.substring(spacePos + 1);
                taskDone(tsk, eventDone);
                break;
            case "list":
                //print list of items
                listTasks(tsk);
                break;
            case "todo":
                String toDoTask = input.substring(spacePos + 1);
                addToDo(tsk, toDoTask);
                break;
            case "deadline":
                String deadlineTask = input.substring(spacePos + 1);
                addDeadline(tsk, deadlineTask);
                break;
            case "event":
                String eventTask = input.substring(spacePos + 1);
                addEvent(tsk, eventTask);
                break;
            default:
                System.out.println(" ☹ OOPS!!! It seems that you have entered a wrong command :-(!");
                break;
            }
            printLine(60);
            input = in.nextLine();
        }
        exit();
    }

    public static void exit() {
        printLine(60);
        System.out.println("\t" + "Bye! Hope to see you again soon!");
        System.out.println("\tYou currently have " + Task.getTaskLeft() + " tasks left");
        System.out.println("\t" + "Happy to help you organize work. Anywhere, anytime!");
        printLine(60);
    }

    private static void addEvent(Task[] tsk, String eventTask) {
        try {
            int slot = eventTask.indexOf("/");
            String description = eventTask.substring(0, slot - 1);
            Event newItem = new Event(description, eventTask.substring(slot + 4));
            tsk[Task.getTaskCount() - 1] = newItem;
            System.out.println("\t" + "Got it. I've added this to your custom-list: ");
            System.out.println("\t\t" + tsk[Task.getTaskCount() - 1].toString());
            System.out.println("\t" + "Now you have " + Task.getTaskCount() + " tasks in your list :)");
        }catch(StringIndexOutOfBoundsException s){
            System.out.println("\t" + "Task.Event should contain a time slot.");
        }
    }

    private static void addDeadline(Task[] tsk, String deadlineTask) {
        try{
        int deadline = deadlineTask.indexOf("/");
        String description = deadlineTask.substring(0, deadline - 1);
        Deadline newItem = new Deadline(description, deadlineTask.substring(deadline + 4));
        tsk[Task.getTaskCount() - 1] = newItem;
        System.out.println("\t" + "Got it. I've added this to your custom-list: ");
        System.out.println("\t\t" + tsk[Task.getTaskCount() - 1].toString());
        System.out.println("\t" + "Now you have " + Task.getTaskCount() + " tasks in your list :)");
        }catch(StringIndexOutOfBoundsException s){
            System.out.println("\t" + "Task.Deadline should contain a due date for the task.");
        }
    }

    private static void addToDo(Task[] tsk, String input) {
        if (input.equals("todo")){
            System.out.println("\t ☹ OOPS!!! The description of a todo cannot be empty.");
        }else {
            ToDo newItem = new ToDo(input);
            tsk[Task.getTaskCount() - 1] = newItem;
            System.out.println("\t Got it. I've added this to your custom-list: ");
            System.out.println("\t\t" + tsk[Task.getTaskCount() - 1].toString());
            System.out.println("\t Now you have " + Task.getTaskCount() + " tasks in your list :)");
        }
    }
//comment
    private static void taskDone(Task[] tsk, String eventDone) {
        int index = extractTaskNumber(eventDone);
        if (index == 0 | index > Task.getTaskCount()) {
            System.out.println("\t" + "Invalid task number");
        } else {
            tsk[index - 1].markAsDone();
            Task.markTaskCompleted();
            System.out.println("\t" + "Nice! I've marked this task as done:");
            System.out.println("\t\t" + tsk[index - 1].toString());
            System.out.println("\t" + "Type \"list\" to see a list of pending tasks");
        }
    }

    private static void addTask(Task[] tsk, String taskDescription) {
        Task newTask = new Task(taskDescription);
        tsk[Task.getTaskCount() - 1] = newTask;
    }

    private static void listTasks(Task[] tsk) {
        if (Task.getTaskCount() == 0) {
            System.out.println("\t" + "You currently have no tasks");
            System.out.println("\t" + "To update your to-do list, just type the task");
        } else {
            System.out.println("\t" + "Here are the tasks in your list:");
            for (int j = 0; j < Task.getTaskCount(); j++) {
                System.out.println("\t" + (j + 1) + ". " + tsk[j].toString());
            }
        }
    }

    private static int extractTaskNumber(String command) {
        int itemNo;
        try {
            itemNo = Integer.parseInt(command);
        } catch (NumberFormatException e) {
            itemNo = 0;
        }
        return itemNo;
    }
}
