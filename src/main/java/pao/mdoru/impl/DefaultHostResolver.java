package main.java.pao.mdoru.impl;


import main.java.pao.mdoru.interfaces.HostResolver;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by m-doru on 21.04.2016.
 */
public class DefaultHostResolver implements HostResolver {

    public InetSocketAddress resolve(String host, int port)
            throws UnknownHostException {
        InetAddress addr = InetAddress.getByName(host);
        return new InetSocketAddress(addr,port);
    }
}
