package pao.mdoru.interfaces;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by m-doru on 21.04.2016.
 */
public interface HttpProxyServerBootstrap {
    /**
     * @param name Server name, useful for logging
     * @return
     */
    HttpProxyServerBootstrap withName(String name);
    /**
     *
     * @param port : Port to listen for incomming connections
     * @return
     */

    HttpProxyServerBootstrap withPort(int port);

    /**
     *
     * @param backlog Size of the request queue
     * @return
     */
    HttpProxyServerBootstrap withBacklog(int backlog);


    HttpProxyServer start() throws Exception;
}
