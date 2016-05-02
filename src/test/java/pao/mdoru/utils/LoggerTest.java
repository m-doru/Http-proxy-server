package pao.mdoru.utils;

import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by m-doru on 02.05.2016.
 */
public class LoggerTest {
    @Test
    public void log_someData_writesDataToTheLogFile() throws Exception {
        String directory = "directory";
        String fileName = "fileLog";

        Path dirPath = FileSystems.getDefault().getPath(directory);
        Path filePath = FileSystems.getDefault().getPath(directory, fileName);

        assertFalse(Files.exists(dirPath));
        assertFalse(Files.exists(filePath));

        Logger logger = new Logger(directory, fileName);

        assertTrue(Files.exists(dirPath));
        assertTrue(Files.exists(filePath));

        logger.log("test");
        logger.flush();

        String fileLogContent = new String(Files.readAllBytes(filePath));

        assertEquals("test", fileLogContent);

        Files.delete(filePath);

        Files.delete(dirPath);
    }

}