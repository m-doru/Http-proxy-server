package pao.mdoru.interfaces;

import java.net.InetSocketAddress;

/**
 * Created by m-doru on 21.04.2016.
 */
public interface HttpProxyServer {
    public void stop();
    public InetSocketAddress getListenAddress();
}

