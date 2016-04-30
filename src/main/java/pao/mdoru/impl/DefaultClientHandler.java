package pao.mdoru.impl;


import pao.mdoru.interfaces.RequestHandler;
import pao.mdoru.utils.ByteBuilder;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by m-doru on 21.04.2016.
 */
public class DefaultClientHandler implements Runnable{
    private final Logger LOGGER = Logger.getLogger(DefaultClientHandler.class.getName());
    private Socket clientSocket;
    private InputStream clientInput;
    private OutputStream clientOutput;

    public DefaultClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientInput = clientSocket.getInputStream();
        this.clientOutput = clientSocket.getOutputStream();
    }

    @Override
    public void run(){
        String requestHeader = this.readHeader();

        HttpRequest request = new HttpRequest(requestHeader);

        //TODO if it is a post request we should read more into the request
        if(request.hasContent()){
            try {
                request.setContent(this.readContent(request.getContentLength()));
            } catch (IOException e) {
                this.LOGGER.log(Level.SEVERE, "Failed to read content. Ignoring request");
                try {
                    this.clientSocket.close();
                    this.clientInput.close();
                    this.clientOutput.close();
                } catch (IOException e1) {
                    this.LOGGER.log(Level.WARNING, "Problems when disconnecting client");
                }
                return;
            }
        }

        RequestHandler requestHandler = new HttpRequestHandler();

        requestHandler.handle(request);

        String requestAnswer = requestHandler.getAnswer();

        try {
            this.clientOutput.write(requestAnswer.getBytes());

        } catch (IOException e) {
            System.out.println("Failed to write to client stream");
        }

        try{
            this.clientSocket.close();
        }
        catch (IOException e){
            System.out.println("Failed to close the client socket");
        }

    }

    private String readHeader(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.clientInput));

        String line;

        StringBuilder requestBuilder = new StringBuilder();
        try {
            while((line = reader.readLine()) != null){
                requestBuilder.append(line);
                requestBuilder.append("\n");

                if(line.length() == 0)
                    break;
            }
        } catch (IOException e) {
            this.LOGGER.log(Level.SEVERE, "Problem reading from client socket");
            return null;
        }
        return requestBuilder.toString();
    }

    private byte[] readContent(int contentLength) throws IOException {
        ByteBuilder contentBuilder = new ByteBuilder();

        byte[] buffer = new byte[1024];

        int sizeRead = 0;

        while(contentLength > 0){
            sizeRead = this.clientInput.read(buffer);

            contentBuilder.append(buffer, Math.min(sizeRead, contentLength));
            contentLength -= sizeRead;
        }

        return contentBuilder.toArray();
    }
}
