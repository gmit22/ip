package tasks;

import file.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;


public class TaskManager {

    private static final int MAX_TASKS = 100;
    private static final ArrayList<Task> taskList = new ArrayList<>(MAX_TASKS);
    private static int taskCount = 0;
    private static int taskLeft = 0;
    private final FileManager fileManager;

    public TaskManager(FileManager fileManager) throws IOException {
        this.fileManager = fileManager;
        fileManager.parseFile(this);
    }


    private static Task addTask(Task task) {
        taskList.add(task);
        taskCount++;
        taskLeft++;
        return task;
    }
    public static Task addEvent(String description, String at) {
        Event event = new Event(description, at);
        return addTask(event);
    }
    public static Task addDeadline(String description, String by) {
        Deadline deadline = new Deadline(description, by);
        return addTask(deadline);
    }
    public static Task addToDo(String description) {
        ToDo todo = new ToDo(description);
        return addTask(todo);
    }
    public static void listTasks() {
        if (taskCount == 0) {
            System.out.println("\t" + "You currently have no tasks");
            System.out.println("\t" + "To update your to-do list, just type the task");
        } else {
            System.out.println("\t" + "Here are the tasks in your list:");
            for (int j = 0; j < taskCount; j++) {
                System.out.println("\t" + (j + 1) + ". " + taskList.get(j).toString());
            }
        }
    }
    public static Task deleteTask(int id) {
        Task task = taskList.get(id - 1);
        taskList.remove(id - 1);
        taskCount--;
        if (!task.isDone) {
            taskLeft--;
        }
        return task;
    }
    public static Task markAsDone(int id) {
        Task task = taskList.get(id - 1);
        if (!task.isDone) {
            taskLeft--;
        }
        task.markAsDone();
        return task;
    }
    /**
     * @return number of tasks
     */
    public int getTaskCount() {
        return taskCount;
    }
    /**
     * @return taskLeft to be done
     */
    public int getTaskLeft() {
        return taskLeft;
    }
    private Task getTask(int id) {
        return taskList.get(id);
    }

    public void parseLines(BufferedReader br) throws IOException {

        String fileLine;
        while ((fileLine = br.readLine()) != null) {
            String[] taskDetails = fileLine.trim().split("\\s*[|]\\s*");

            Task task;
            switch (taskDetails[0]) {
            case "T":
                task = addToDo(taskDetails[2]);
                break;
            case "E":
                task = addEvent(taskDetails[2], taskDetails[3]);
                break;
            case "D":
                task = addDeadline(taskDetails[2], taskDetails[3]);
                break;
            default:
                return;
            }

            if (taskDetails[1].equals("1")) {
                taskLeft--;
                task.markAsDone();
            }
        }
    }


    public void writeToFile() throws IOException {
        StringBuilder lines = new StringBuilder();
        String taskType, isDone, desc, param;
        String delimiter = " | ";
        boolean hasParam;
        //System.out.println("function called");

        for (int i = 0; i < taskCount; i++) {
            Task task = getTask(i);
            //System.out.println("function called");
            if (task instanceof ToDo) {
                taskType = "T";
                desc = task.getDescription();
                param = "";
                hasParam = false;
            } else if (task instanceof Deadline) {
                taskType = "D";
                desc = task.getDescription();
                param = ((Deadline) task).getBy();
                hasParam = true;
            } else if (task instanceof Event) {
                taskType = "E";
                desc = task.getDescription();
                param = ((Event) task).getAt();
                hasParam = true;
            } else {
                return;
            }
            isDone = task.getIsDone() ? "1" : "0";
            lines.append(taskType).append(delimiter).append(isDone).append(delimiter).append(desc);

            if (hasParam) {
                lines.append(delimiter).append(param);
            }
            lines.append("\n");
        }
        fileManager.saveToFile(lines.toString());

    }
}
