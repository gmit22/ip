package file;

import tasks.TaskManager;

import java.io.*;

public class FileManager {

    private String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }
    public static void createDirectory(String dirPath) throws IOException {
        File file = new File(dirPath);
        try {
            if (file.mkdir()) {
                System.out.println("Directory created at location: " + file.getCanonicalPath());
            } else {
                System.out.println("Directory already exists at location: " + file.getCanonicalPath());
            }
        } catch (IOException e) {
            System.out.println(e);
            throw e;
        }
    }
    public String getFilePath() {
        return filePath;
    }
    public void createFile() throws Exception {
        File file = new File(filePath);

        try {
            if (file.createNewFile()) {
                System.out.println("File created at location: " + file.getCanonicalPath());
            } else {
                System.out.println("File already exists at location: " + file.getCanonicalPath());
            }
        } catch (IOException e) {
            System.out.println(e);
            throw e;
        }
    }
    public void parseFile(TaskManager taskList) throws FileNotFoundException {

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

    public void saveToFile(String line) throws IOException {
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
