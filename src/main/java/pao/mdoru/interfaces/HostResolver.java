package main.java.pao.mdoru.interfaces;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by m-doru on 21.04.2016.
 */
public interface HostResolver {
    InetSocketAddress resolve(String host, int port) throws UnknownHostException;
}