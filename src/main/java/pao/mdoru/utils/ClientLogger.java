package pao.mdoru.utils;

import java.io.FileWriter;
import java.nio.file.Path;

/**
 * Created by m-doru on 02.05.2016.
 */
public class ClientLogger extends Logger{
    private static final String FILENAME = "client.log";
    private Path directory;
    private Path logFile;
    private FileWriter fileWriter;

    public ClientLogger(String destinationDirectoryName) {
        super(destinationDirectoryName, FILENAME);
    }

}
