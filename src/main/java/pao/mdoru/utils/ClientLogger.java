package pao.mdoru.utils;

import java.io.FileWriter;
import java.nio.file.Path;

/**
 * Created by m-doru on 02.05.2016.
 */
public class ClientLogger extends Logger{
    private static final String FILENAME = "client.log";

    public ClientLogger(final String destinationDirectoryName) {
        super(destinationDirectoryName, FILENAME);
    }

}
