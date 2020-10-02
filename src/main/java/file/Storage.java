package file;

import ui.Ui;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    /*path for home directory*/
    private static final String ROOT = System.getProperty("user.dir");
    private static final Path FILE_PATH = Paths.get(ROOT, "data", "data.txt");
    private static final Path DIR_PATH = Paths.get(ROOT, "data");
    private static final boolean DIRECTORY_EXISTS = Files.exists(DIR_PATH);

    /**
     * @param ui To display messages to the user, if any.
     * @return FileManager object to be used for creating a task manager.
     */
    public static FileManager storage(Ui ui) {
        FileManager fileManager;
        File file = new File(String.valueOf(DIR_PATH));

        if (!DIRECTORY_EXISTS) {
            file.mkdirs();
        }
        fileManager = new FileManager(FILE_PATH.toString());
        return fileManager;
    }
}
