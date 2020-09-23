package file;

import exception.DukeException;
import tasks.TaskManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class FileManager {

    private final String filePath;
    /**
     * FileManager constructor, used to write and read to designated file.
     * @param filePath String path of the file.
     */
    public FileManager(String filePath) {
        this.filePath = filePath;
    }
    /**
     * @return String FilePath of the .txt file.
     */
    public String getFilePath() {
        return filePath;
    }
    /**
     * Creates a file at filepath if not already existing.
     * @throws IOException If parent directory not present/ invalid path.
     */
    public void createFile() throws  IOException {
        File file = new File(filePath);

        try {
            if (file.createNewFile()) {
                System.out.println("\tFile created at location: " + file.getCanonicalPath());
            } else {
                System.out.println("\tFile already exists at location: " + file.getCanonicalPath());
            }
        } catch (IOException e) {
            throw e;
        }
    }
    /**
     * Reads the .txt file, sends data to parse it.
     * @param taskList TaskList to which tasks are added, and stored.
     * @throws DukeException
     * @throws FileNotFoundException
     */
    public void parseFile(TaskManager taskList) throws DukeException, FileNotFoundException {

        FileInputStream fstream;
        fstream = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        try {
            taskList.parseLines(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Updates the .txt file after every command, through TaskManager.writeToFIle().
     * @param line String input to write to the file.
     */
    public void saveToFile(String line) {
        File file = new File(filePath);
        FileWriter fr = null;

        try {
            fr = new FileWriter(file);
            fr.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
