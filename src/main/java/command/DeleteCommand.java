package command;

import tasks.Task;
import tasks.TaskManager;

public class DeleteCommand {
    /**
     * @param taskManager To edit taskList linked to task manager, update .txt file.
     * @param input       input of the task to be deleted.
     * @return Task object deleted from the taskList.
     */
    public static Task deleteTask(TaskManager taskManager, String input) {
        Task task = null;
        int id = Integer.parseInt(input.substring(7));

        if (id <= taskManager.getTaskCount()) {
            task = taskManager.taskList.get(id - 1);
            taskManager.taskCount--;
            if (!task.isDone) {
                taskManager.taskLeft--;
            }
            taskManager.taskList.remove(id - 1);
        }
        return task;
    }
}
