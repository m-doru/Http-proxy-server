package pao.mdoru.impl;

import pao.mdoru.interfaces.HttpProxyServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
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

    }
}
