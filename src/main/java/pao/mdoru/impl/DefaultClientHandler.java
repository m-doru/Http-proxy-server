package pao.mdoru.impl;

import java.net.Socket;

/**
 * Created by m-doru on 21.04.2016.
 */
public class DefaultClientHandler {
    private Socket clientSocket;

    public DefaultClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void handle(){
        //TODO! here is handled the request from client
    }


}
