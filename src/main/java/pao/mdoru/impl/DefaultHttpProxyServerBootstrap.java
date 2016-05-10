package pao.mdoru.impl;

import pao.mdoru.interfaces.HostResolver;
import pao.mdoru.interfaces.HttpProxyServer;
import pao.mdoru.interfaces.HttpProxyServerBootstrap;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by m-doru on 10.05.2016.
 */
public class DefaultHttpProxyServerBootstrap implements HttpProxyServerBootstrap {
    String name = "noiceprxy";
    int port = 32165;
    int backlog = 500;

    /**
     *
     * @param name Server name, useful for logging. Default value is noiceprxy
     * @return
     */
    @Override
    public HttpProxyServerBootstrap withName(String name) {
        this.name = name;
        return this;
    }

    /**
     *
     * @param port : Port to listen for incomming connections. Default value is 32165
     * @return
     */
    @Override
    public HttpProxyServerBootstrap withPort(int port) {
        this.port = port;
        return this;
    }

    /**
     *
     * @param backlog Size of the request queue. Default value is 500
     * @return
     */
    @Override
    public HttpProxyServerBootstrap withBacklog(int backlog){
        this.backlog = backlog;
        return this;
    }

    @Override
    public HttpProxyServer start() throws IOException {
        Logger.getLogger(this.name).log(Level.INFO, "Starting server with the following settings:\n" +
                "name: " + this.name + "\n" +
                "port: " + this.port + "\n" +
                "backlog: " + this.backlog + "\n");
        return new DefaultHttpProxyServer(this.name, this.port, this.backlog);
    }
}
