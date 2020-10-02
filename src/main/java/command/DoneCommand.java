package command;

import exception.DukeException;
import exception.ExceptionType;
import tasks.Task;
import tasks.TaskManager;

public class DoneCommand {
    /**
     * @param taskNumber TaskNumber to be deleted from the list.
     * @return Task marked as done.
     * @throws DukeException If invalid taskNumber input by the user.
     */
    public static Task taskDone(TaskManager taskManager, int taskNumber) throws DukeException {
        if (taskNumber <= 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if (taskNumber > taskManager.getTaskCount()) {
            throw new DukeException(ExceptionType.INVALID_NUMBER);
        } else {
            Task task = taskManager.getTask(taskNumber - 1);
            if (!task.isDone) {
                taskManager.taskLeft--;
            }
            task.markAsDone();
            return task;
        }
    }
}
