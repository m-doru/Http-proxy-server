package pao.mdoru.impl;

import pao.mdoru.interfaces.HttpProxyServer;
import pao.mdoru.utils.HttpProxyLogger;
import pao.mdoru.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by m-doru on 21.04.2016.
 */
public class DefaultHttpProxyServer implements HttpProxyServer {
    private final Logger LOGGER = new HttpProxyLogger();
    private String name;
    private volatile ServerSocket serverSocket;
    private int port;
    private volatile Thread socketListenerThread;
    private ExecutorService clientHandler;

    public DefaultHttpProxyServer(final String name, final int port, final int threadPoolSize) throws IOException {
        this.name = name;
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
        this.clientHandler = Executors.newFixedThreadPool(threadPoolSize);
        this.socketListenerThread = new Thread(() -> {this.socketListener();});
        this.socketListenerThread.start();
    }
    public void stop() {
        this.socketListenerThread.interrupt();
        try {
            this.serverSocket.close();
        } catch (IOException e) {

        }
    }


    private void socketListener(){
        while(!this.socketListenerThread.isInterrupted()){
            Socket client;
            try {
                client = this.serverSocket.accept();
            } catch (IOException e) {
                this.LOGGER.log("Failed to accept connection");
                continue;
            }
            try {
                DefaultClientHandler handler = new DefaultClientHandler(client);
                this.clientHandler.execute(handler);
            } catch (IOException e) {
                this.LOGGER.log("Failed to instantiate a ClientHandler");
                continue;
            }
        }
        this.LOGGER.flush();
        System.out.println("Server stopped");
        System.exit(0);
    }
}
