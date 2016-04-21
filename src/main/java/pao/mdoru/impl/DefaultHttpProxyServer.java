package pao.mdoru.impl;

import pao.mdoru.interfaces.HttpProxyServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * Created by m-doru on 21.04.2016.
 */
public class DefaultHttpProxyServer implements HttpProxyServer{
    private InetSocketAddress boundAddress;
    private String name;
    private ServerSocket serverSocket;
    private int port;
    private Thread socketListenerThread;
    private Runnable socketListener;
    public DefaultHttpProxyServer(String name, int port, InetSocketAddress boundAddress) throws IOException {
        this.name = name;
        this.port = port;
        this.boundAddress = boundAddress;
        this.serverSocket = new ServerSocket(this.port);
        this.socketListener = () -> {System.out.println("ceva");};
        this.socketListenerThread = new Thread(socketListener);
        this.socketListenerThread.start();
    }
    public void stop() {
        this.socketListenerThread.interrupt();
    }

    public InetSocketAddress getListenAddress() {
        return this.boundAddress;
    }

    private void socketListenerMethod(){

    }
}
