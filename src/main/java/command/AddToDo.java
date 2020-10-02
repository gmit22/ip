package command;

import exception.DukeException;
import exception.ExceptionType;
import tasks.Task;
import tasks.TaskManager;
import tasks.ToDo;

public class AddToDo {

    /**
     * @param taskManager     To edit taskList linked to task manager, update .txt file.
     * @param toDoDescription Details to instantiate toDoTask.
     * @return Task object added to the taskList.
     * @throws DukeException If invalid input format by the user.
     */
    public static Task addToDo(TaskManager taskManager, String toDoDescription) throws DukeException {
        try {
            ToDo todo = new ToDo(toDoDescription.substring(5).trim());
            Command.addTask(taskManager, todo);
            return todo;
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
    }
}
