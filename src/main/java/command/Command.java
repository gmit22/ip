package command;

import tasks.Task;
import tasks.TaskManager;

public class Command {

    /**
     * Updates taskLeft, taskCount.
     *
     * @param taskManager To edit taskList linked to task manager, update .txt file.
     * @param task        Add task to taskList variable of the class.
     */
    protected static void addTask(TaskManager taskManager, Task task) {
        taskManager.taskList.add(task);
        taskManager.taskCount++;
        taskManager.taskLeft++;
    }
}
