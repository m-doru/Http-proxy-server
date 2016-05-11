package pao.mdoru.impl;

import pao.mdoru.utils.LineInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m-doru on 02.05.2016.
 */
public class HttpRequestAnswerHeader {
    private List<String> parameters = new ArrayList<>();
    private int status;
    private String message;
    private String version;
    public void parseHeader(final InputStream serverInput) throws IOException {
        String firstLine = LineInputStream.readLine(serverInput);
        this.parseFirstLine(firstLine);

        this.parameters.add(firstLine);

        String line;
        while((line = LineInputStream.readLine(serverInput)) != null){
            if(line.length() == 0)
                break;
            this.parameters.add(line);
        }
    }

    private void parseFirstLine(String line){
        String[] data = line.split(" ");
        if(data.length >= 3) {
            this.version = data[0];
            this.status = Integer.parseInt(data[1]);
            StringBuilder messageBuilder = new StringBuilder();
            for(int i = 2; i < data.length; ++i)
                messageBuilder.append(data[i] + " ");
            this.message = messageBuilder.toString().trim();
        }
    }

    public void addParameter(String parameter){
        this.parameters.add(parameter);
    }

    public List<String> getParameters(){
        return this.parameters;
    }

    public int getStatusCode(){
        return this.status;
    }

    public String getMessage(){
        return this.message;
    }

    public String getVersion(){
        return this.version;
    }
}
