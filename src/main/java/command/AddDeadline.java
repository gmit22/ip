package command;

import exception.DukeException;
import exception.ExceptionType;
import tasks.Deadline;
import tasks.Task;
import tasks.TaskManager;

public class AddDeadline {

    /**
     * @param taskManager  To edit taskList linked to task manager, update .txt file.
     * @param deadlineTask Details to instantiate deadlineTask.
     * @return Task object added to the taskList.
     * @throws DukeException If invalid input format by the user.
     */
    public static Task addDeadline(TaskManager taskManager, String[] deadlineTask) throws DukeException {
        if (deadlineTask[0].equals("deadline")) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
        if (deadlineTask.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_TIME);
        }
        String description = deadlineTask[0].trim();
        String by = deadlineTask[1].trim();
        Deadline deadline = new Deadline(description, by);
        Command.addTask(taskManager, deadline);
        return deadline;
    }
}
