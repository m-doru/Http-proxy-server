package pao.mdoru.impl;


import pao.mdoru.utils.ClientLogger;
import pao.mdoru.utils.LineInputStream;

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
    private InputStream clientReader;
    public DefaultClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.clientReader = this.clientSocket.getInputStream();
    }

    @Override
    public void run(){
        String requestHeader = this.readHeader();

        ClientLogger logger = new ClientLogger("client");
        logger.log(requestHeader);
        logger.flush();

        HttpRequestHeader request = new HttpRequestHeader(requestHeader);


        HttpRequestHandler requestHandler = new HttpRequestHandler(this.clientSocket);

        try {
            requestHandler.handle(request);
        } catch (IOException e) {
            this.LOGGER.log(Level.SEVERE, "Failed to handle the request. Aborting");
            e.printStackTrace();
        }

        this.closeConnection();
    }
    private void closeConnection(){
        try{
            this.clientSocket.close();
        }
        catch (IOException e){
            this.LOGGER.log(Level.SEVERE, "Failed to close the client socket");
        }
    }

    private String readHeader(){

        String line;

        StringBuilder requestBuilder = new StringBuilder();
        try {
            while((line = LineInputStream.readLine(this.clientReader))   != null){
                requestBuilder.append(line);
                requestBuilder.append("\n");

                if(line.length() == 0)
                    break;
            }
        } catch (IOException e) {
            this.LOGGER.log(Level.SEVERE, "Fail to read from client socket");
            return null;
        }
        return requestBuilder.toString();
    }

}
