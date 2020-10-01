package tasks;

import exception.DukeException;
import exception.ExceptionType;
import file.FileManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TaskManager {

    private static final int MAX_TASKS = 100;
    private static final ArrayList<Task> taskList = new ArrayList<>(MAX_TASKS);
    private static int taskCount = 0;
    private static int taskLeft = 0;
    private final FileManager fileManager;
    /**
     * Constructor.
     * @param fileManager FileManager object having location of file.
     * @throws DukeException Exception raised while parsing file, to add tasks to the TaskManager.
     * @throws FileNotFoundException If file not found while converting into stream.
     */
    public TaskManager(FileManager fileManager) throws DukeException, FileNotFoundException {
        this.fileManager = fileManager;
        fileManager.parseFile(this);
    }
    /**
     * Updates taskLeft, taskCount.
     * @param task Add task to taskList variable of the class.
     */
    private static void addTask(Task task) {
        taskList.add(task);
        taskCount++;
        taskLeft++;
    }
    /**
     * @param deadlineTask Details to instantiate deadlineTask.
     * @return Task object added to the taskList.
     * @throws DukeException If invalid input format by the user.
     */
    public static Task addDeadline(String[] deadlineTask) throws DukeException {
        if (deadlineTask[0].equals("deadline")) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
        if (deadlineTask.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_TIME);
        }
        String description = deadlineTask[0].trim();
        String by = deadlineTask[1].trim();
        Deadline deadline = new Deadline(description, by);
        addTask(deadline);
        return deadline;
    }
    /**
     * @param toDoDescription Details to instantiate toDoTask.
     * @return Task object added to the taskList.
     * @throws DukeException If invalid input format by the user.
     */
    public static Task addToDo(String toDoDescription) throws DukeException {
        try {
            ToDo todo = new ToDo(toDoDescription.substring(5).trim());
            addTask(todo);
            return todo;
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
    }
    /**
     * @param eventTask Details to instnatiate eventTask.
     * @return Task object added to the taskList
     * @throws DukeException If invalid input format by the user.
     */
    public static Task addEvent(String[] eventTask) throws DukeException {
        if (eventTask[0].equals("event")) {
            throw new DukeException(ExceptionType.MISSING_TASK_DESCRIPTION);
        }
        if (eventTask.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_TIME);
        }
        String description = eventTask[0].trim();
        String at = eventTask[1].trim();
        Event event = new Event(description, at);
        addTask(event);
        return event;
    }
    /**
     * @param keyword Keyword to match the taskDescription of objects in taskList.
     * @return ArrayList of tasks matching the keyword.
     */
    public static ArrayList<Task> findTask(String keyword){
        ArrayList<Task> foundTasks = new ArrayList<>(MAX_TASKS);
        keyword = keyword.substring(5).trim();
        for (Task task: taskList){
            if (task.description.contains(keyword)){
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }
    /**
     * @param input input of the task to be deleted.
     * @return Task object deleted from the taskList.
     */
    public static Task deleteTask(String input) {
        Task task= null;
        int id = Integer.parseInt(input.substring(7));

        if (id <= taskCount) {
            task = taskList.get(id - 1);
            taskList.remove(id - 1);
            taskCount--;
            if (!task.isDone) {
                taskLeft--;
            }
        }
        return task;
    }
    /**
     * @param taskNumber TaskNumber to be deleted from the list.
     * @return Task marked as done.
     * @throws DukeException If invalid taskNumber input by the user.
     */
    public static Task taskDone(int taskNumber) throws DukeException {
        if (taskNumber <= 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if (taskNumber > taskCount) {
            throw new DukeException(ExceptionType.INVALID_NUMBER);
        } else {

            return TaskManager.markAsDone(taskNumber);
        }
    }
    /**
     * Mark relevant task as done.
     * @param id of task to be marked as done.
     * @return Task object of taskId.
     */
    public static Task markAsDone(int id) {
        Task task = taskList.get(id - 1);

        if (!task.isDone) {
            taskLeft--;
        }
        task.markAsDone();
        return task;
    }
    /**
     * @return Number of tasks.
     */
    public int getTaskCount() {
        return taskCount;
    }
    /**
     * @return taskLeft to be done.
     */
    public int getTaskLeft() {
        return taskLeft;
    }
    /**
     * @param id Id of task to be accessed in the taskList.
     * @return Task object of relevant id.
     */
    public Task getTask(int id) {
        return taskList.get(id);
    }
    /**
     * @param br Input to parse the file.
     * @throws IOException If error in processing br input.
     * @throws DukeException If missing details of task.
     */
    public void parseLines(BufferedReader br) throws IOException, DukeException {
        String fileLine;

        while ((fileLine = br.readLine()) != null) {
            String[] taskDetails = fileLine.trim().split("\\s*[|]\\s*");
            Task task;

            switch (taskDetails[0]) {
            case "T":
                task = addToDo(taskDetails[2]);
                break;
            case "E":
                task = addEvent(Arrays.copyOfRange(taskDetails, 2, 4));
                break;
            case "D":
                task = addDeadline(Arrays.copyOfRange(taskDetails, 2, 4));
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
    /**
     * Updates the .txt file with objects changed/added to taskList.
     * @throws IOException
     */
    public void writeToFile() throws IOException {
        StringBuilder lines = new StringBuilder();
        String taskType, isDone, desc, param;
        String delimiter = " | ";
        boolean hasParam;

        for (int i = 0; i < taskCount; i++) {
            Task task = getTask(i);

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
    /**
     * Creates a data.txt file if not existing.
     * Creates a TaskManager object to update and retrieve data from the .txt file.
     **/
    public static TaskManager createTaskManager(FileManager fileManager) throws IOException {
        // Will loop as long as FileNotFoundException is caught, and file is not created
        while (true) {
            try {
                return new TaskManager(fileManager);
            } catch (FileNotFoundException | DukeException e) {
                // Create file if not found
                try {
                    fileManager.createFile();
                } catch (IOException err) {
                    throw err;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}
