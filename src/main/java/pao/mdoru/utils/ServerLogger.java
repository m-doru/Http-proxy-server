package pao.mdoru.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by m-doru on 02.05.2016.
 */
public class ServerLogger extends Logger{
    private static final String FILENAME = "server.log";
    private Path directory;
    private Path logFile;
    private FileWriter fileWriter;

    public ServerLogger(String destinationDirectoryName){
        super(destinationDirectoryName, FILENAME);
    }
}
