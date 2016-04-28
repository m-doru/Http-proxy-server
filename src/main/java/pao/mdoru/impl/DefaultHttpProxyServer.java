package main.java.pao.mdoru.impl;

import main.java.pao.mdoru.interfaces.ClientHandler;
import main.java.pao.mdoru.interfaces.HttpProxyServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by m-doru on 21.04.2016.
 */
public class DefaultHttpProxyServer implements HttpProxyServer{
    private final Logger LOGGER;
    private InetSocketAddress boundAddress;
    private String name;
    private ServerSocket serverSocket;
    private int port;
    private Thread socketListenerThread;
    private Runnable socketListenerRunnable;
    public DefaultHttpProxyServer(String name, int port, InetSocketAddress boundAddress) throws IOException {
        this.name = name;
        this.port = port;
        this.boundAddress = boundAddress;
        this.serverSocket = new ServerSocket(this.port);
        this.socketListenerRunnable = () -> {
            this.socketListener();
        };
        this.socketListenerThread = new Thread(socketListenerRunnable);
        this.socketListenerThread.start();
        this.LOGGER = Logger.getLogger(this.name);
    }
    public void stop() {
        this.socketListenerThread.interrupt();
    }

    public InetSocketAddress getListenAddress() {
        return this.boundAddress;
    }

    private void socketListener(){
        while(!this.socketListenerThread.isInterrupted()){
            Socket client;
            try {
                client = this.serverSocket.accept();
            } catch (IOException e) {
                this.LOGGER.log(Level.WARNING, "Failed to accept connection");
                continue;
            }
            try {
                ClientHandler clientHandler = new DefaultClientHandler(client);
                clientHandler.handle();
            } catch (IOException e) {
                this.LOGGER.log(Level.WARNING, "Failed to instantiate a ClientHandler");
                continue;
            }


        }
    }
}
