package pao.mdoru.impl;

import pao.mdoru.interfaces.ClientHandler;
import pao.mdoru.interfaces.RequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by m-doru on 21.04.2016.
 */
public class DefaultClientHandler extends Thread implements ClientHandler{
    private final Logger LOGGER = Logger.getLogger(DefaultClientHandler.class.getName());
    private Socket clientSocket;
    private InputStream clientInput;
    private OutputStream clientOutput;

    public DefaultClientHandler(Socket clientSocket) throws IOException {
        super("ClientHandler");
        this.clientSocket = clientSocket;
        this.clientInput = clientSocket.getInputStream();
        this.clientOutput = clientSocket.getOutputStream();
    }

    public void handle() {
        this.start();
    }
    @Override
    public void run(){
        byte[] requestBuffer;
        try{
            requestBuffer = new byte[this.clientInput.available()];
        } catch (IOException e) {
            this.LOGGER.log(Level.SEVERE, "Failed to get the input stream available size");
            try {
                this.clientInput.close();
                this.clientOutput.close();
            } catch (IOException e1) {
                this.LOGGER.log(Level.SEVERE, "Failed to close socket streams");
            }
            return;
        }

        RequestHandler requestHandler = new HttpRequestHandler();
        requestHandler.handle(new HttpRequest(requestBuffer));
    }

}
