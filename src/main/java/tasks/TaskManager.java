package tasks;

import exception.DukeException;
import file.FileManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static command.AddDeadline.addDeadline;
import static command.AddEvent.addEvent;
import static command.AddToDo.addToDo;

public class TaskManager {

    private static final int MAX_TASKS = 100;
    public  ArrayList<Task> taskList = new ArrayList<>(MAX_TASKS);
    public int taskCount = 0;
    public int taskLeft = 0;
    private final FileManager fileManager;

    /**
     * Constructor.
     *
     * @param fileManager FileManager object having location of file.
     * @throws DukeException         Exception raised while parsing file, to add tasks to the TaskManager.
     * @throws FileNotFoundException If file not found while converting into stream.
     */
    public TaskManager(FileManager fileManager) throws DukeException, FileNotFoundException {
        this.fileManager = fileManager;
        fileManager.parseFile(this);
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
     * @throws IOException   If error in processing br input.
     * @throws DukeException If missing details of task.
     */
    public void parseLines(BufferedReader br) throws IOException, DukeException {
        String fileLine;

        while ((fileLine = br.readLine()) != null) {
            String[] taskDetails = fileLine.trim().split("\\s*[|]\\s*");
            Task task;

            switch (taskDetails[0]) {
            case "T":
                task = addToDo(this, "     " + taskDetails[2]);
                break;
            case "E":
                task = addEvent(this, Arrays.copyOfRange(taskDetails, 2, 4));
                break;
            case "D":
                task = addDeadline(this, Arrays.copyOfRange(taskDetails, 2, 4));
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
     *
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
}
