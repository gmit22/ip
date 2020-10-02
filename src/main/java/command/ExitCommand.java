package command;

import tasks.TaskManager;

import java.io.IOException;

public class ExitCommand {

    public static void exit(TaskManager taskManager) {
        try {
            taskManager.writeToFile();
        } catch (IOException e) {
            System.out.println(" Error in reading/writing the file");
        }
    }

}
