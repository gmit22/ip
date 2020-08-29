import java.util.Scanner;


public class Duke {
    static String INDENT = "    ";

    public static void printLine(int n) {
        System.out.println(INDENT + "-".repeat(n));
    }

    public static void printGreeting() {
        printLine(60);
        System.out.println(INDENT + "Hello! I'm Duke \n    What can I do for you?");
        printLine(60);
    }

    public static void exit() {
        printLine(60);
        System.out.println(INDENT + "Bye! Hope to see you again soon!");
        printLine(60);
    }

    public static void main(String[] args) {
        printGreeting();
        Task[] tsk = new Task[100];
        Scanner in = new Scanner(System.in);
        String input;
        input = in.nextLine();
        String command;

        int spacePos;
        String eventDone;

        while (!input.equals("bye")) {
            spacePos = input.indexOf(" ");
            command = spacePos > 0 ? input.substring(0, spacePos) : input;

            switch (command) {
            case "bye":
                exit();
                break;
            case "done":
                eventDone = input.substring(spacePos + 1);
                taskDone(tsk, eventDone);
                break;
            case "list":
                //print list of items
                listTasks(tsk);
                break;
            default:
                addTask(tsk, input);
                break;
            }
            input = in.nextLine();
        }
    }

    private static void taskDone(Task[] tsk, String eventDone) {
        int index = extractTaskNumber(eventDone);

        if (index == 0 | index > Task.getTaskCount()) {
            printLine(60);
            System.out.println(INDENT + "Invalid task number");
        }else {
            tsk[index - 1].markAsDone();
            printLine(60);
            System.out.println(INDENT + "Nice! I've marked this task as done:");
            System.out.println(INDENT + "  [" + tsk[index - 1].getStatusIcon() + "] " + tsk[index - 1].description);
            System.out.println(INDENT + "Type \"list\" to see a list of pending tasks");
        }
        printLine(60);
    }

    private static int extractTaskNumber(String command) {
        int itemNo = 0;
        try {
            itemNo = Integer.parseInt(command);
        }catch (NumberFormatException e) {
            itemNo = 0;
        }
        return itemNo;
    }

    private static void addTask(Task[] tsk, String taskDescription) {
        Task newTask = new Task(taskDescription);
        tsk[Task.getTaskCount() - 1] = newTask;
        printLine(60);
        System.out.println(INDENT + "added: " + taskDescription);
        printLine(60);
    }

    private static void listTasks(Task[] tsk) {
        int j;
        if (Task.getTaskCount() == 0) {
            System.out.println(INDENT + "You currently have no tasks");
            System.out.println(INDENT + "To update your to-do list, just type the task");
        }else {
            printLine(60);
            System.out.println(INDENT + "Here are the tasks in your list:");
            for (j = 0; j < Task.getTaskCount(); j++) {
                System.out.println(INDENT + (j + 1) + ".[" + tsk[j].getStatusIcon() + "] " + tsk[j].description);
            }
        }
        printLine(60);
    }
}
