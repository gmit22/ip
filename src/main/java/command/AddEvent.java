package command;

import exception.DukeException;
import exception.ExceptionType;
import tasks.Event;
import tasks.Task;
import tasks.TaskManager;

import static command.Command.addTask;

public class AddEvent {

    /**
     * @param taskManager To edit taskList linked to task manager, update .txt file.
     * @param eventTask   Details to instantiate eventTask.
     * @return Task object added to the taskList
     * @throws DukeException If invalid input format by the user.
     */
    public static Task addEvent(TaskManager taskManager, String[] eventTask) throws DukeException {
        if (eventTask[0].equals("event")) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
        if (eventTask.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_TIME);
        }
        String description = eventTask[0].trim();
        String at = eventTask[1].trim();
        Event event = new Event(description, at);
        addTask(taskManager, event);
        return event;
    }
}
