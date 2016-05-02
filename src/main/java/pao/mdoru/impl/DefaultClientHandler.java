package pao.mdoru.impl;


import pao.mdoru.interfaces.RequestHandler;
import pao.mdoru.utils.ByteBuilder;

import java.io.*;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by m-doru on 21.04.2016.
 */
public class DefaultClientHandler implements Runnable{
    private final Logger LOGGER = Logger.getLogger(DefaultClientHandler.class.getName());
    private Socket clientSocket;
    private BufferedReader clientReader;
    public DefaultClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
    }

    @Override
    public void run(){
        String requestHeader = this.readHeader();

        HttpRequest request = new HttpRequest(requestHeader);

        if(request.hasContent()){
            try {
                request.setContent(this.readContent(request.getContentLength()));
            } catch (IOException e) {
                this.LOGGER.log(Level.SEVERE, "Failed to read content. Ignoring request");
                try {
                    this.clientSocket.close();
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
            this.clientSocket.getOutputStream().write(requestAnswer.getBytes());

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

        String line;

        StringBuilder requestBuilder = new StringBuilder();
        try {
            while((line = clientReader.readLine()) != null){
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
        StringBuilder contentBuilder = new StringBuilder();

        char c;

        for(int i = 0; i < contentLength; ++i){
            c = (char)this.clientReader.read();
            if(c != -1)
                contentBuilder.append(c);
            else
                break;
        }

        return contentBuilder.toString().getBytes();
    }
}
