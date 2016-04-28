package main.java.pao.mdoru.interfaces;

import java.net.InetSocketAddress;

/**
 * Created by m-doru on 21.04.2016.
 */
public interface HttpProxyServerBootstrap {
    /**
     * <p>Give the server a name (used for naming threads, useful for logging) </p>
     *
     * <p> Default = ProxyServer</p>
     *
     * @param name
     * @return
     */
    HttpProxyServerBootstrap withName(String name);

    /**
     *
     * @param address : Address to listen for incoming connections
     * @return
     *
     * <p>Default = localhost:37373 </p>
     */
    HttpProxyServerBootstrap withAddress(InetSocketAddress address);

    /**
     *
     * @param port : Port to listen for incomming connections
     * @return
     */

    HttpProxyServerBootstrap withPort(int port);



    HttpProxyServerBootstrap withServerResolver(HostResolver serverResolver);

    HttpProxyServer start();
}
