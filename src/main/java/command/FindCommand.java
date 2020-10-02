package command;

import tasks.Task;
import tasks.TaskManager;

import java.util.ArrayList;

public class FindCommand {
    /**
     * @param taskManager for accessing taskList linked to task manager object.
     * @param keyword     Keyword to match the taskDescription of objects in taskList.
     * @return ArrayList of tasks matching the keyword.
     */
    public static ArrayList<Task> findTask(TaskManager taskManager, String keyword) {
        int MAX_TASKS = 100;
        ArrayList<Task> foundTasks = new ArrayList<>(MAX_TASKS);
        keyword = keyword.substring(5).trim();
        for (Task task : taskManager.taskList) {
            if (task.getDescription().contains(keyword)) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }
}

