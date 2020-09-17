package tasks;
import java.util.ArrayList;


public class TaskManager {

    private static final int MAX_TASKS = 100;
    private static final ArrayList<Task> taskList = new ArrayList<>();
    private static int taskCount = 0;
    private static int taskLeft = 0;

    /**
    @return number of tasks
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

    private static Task addTask(Task task){
        taskList.add(task);
        taskCount++;
        return task;
    }
    public static Task addEvent(String description, String at) {
        Event event = new Event(description, at);
        return addTask(event);
    }

    public static Task addDeadline(String description, String by){
        Deadline deadline = new Deadline(description, by);
        return addTask(deadline);
    }

    public static Task addToDO(String description){
        ToDo todo = new ToDo(description);
        return addTask(todo);
    }

    public static void listTasks(){
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

    public static Task deleteTask(int id){
        Task task = taskList.get(id-1);
        taskList.remove(id-1);
        taskCount--;
        return task;
    }

    public static Task markAsDone(int id){
        Task task = taskList.get(id-1);
        task.markAsDone();
        return task;
    }

}
