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
public class Logger {
    private Path directory;
    private Path logFile;
    private FileWriter fileWriter;
    private String filename;

    public Logger(String destinationDirectoryName, String filename){
        this.directory = Paths.get(destinationDirectoryName);

        if(!Files.exists(this.directory) || !Files.isDirectory(this.directory))
            try {
                Files.createDirectory(this.directory);
            } catch (IOException e) {}

        this.logFile = FileSystems.getDefault().getPath(this.directory.toString(), filename);


        if(!Files.exists(this.logFile))
            try{
                Files.createFile(this.logFile);

            } catch (IOException e) {

                this.logFile = FileSystems.getDefault().getPath(this.directory.toString(), filename);
            }

        try {
            this.fileWriter = new FileWriter(this.logFile.toString(), true);
        } catch (IOException e) {
            try {
                this.fileWriter = new FileWriter("errors.log", true);
            } catch (IOException e1){
                this.fileWriter = null;
            }
        }
    }

    public void log(String data){
        if(this.fileWriter != null)
            try {
                this.fileWriter.write(data + '\n');
            } catch (IOException e) {
            }
    }

    public void flush(){
        try {
            this.fileWriter.flush();
        } catch (IOException e) {
        }
    }
}
