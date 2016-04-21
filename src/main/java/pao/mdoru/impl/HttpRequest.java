package pao.mdoru.impl;

import pao.mdoru.interfaces.Request;

/**
 * Created by m-doru on 21.04.2016.
 */
public class HttpRequest implements Request {
    byte[] rawRequest;
    String host;
    int port;
    String request;
    @Override
    public void fromByteArray(byte[] buffer) {
        //TODO parse the buffer with the request and initialize the fields
        this.rawRequest = buffer;
        String stringRequest = new String(buffer);
        String[] lines = stringRequest.split("\\r?\\n");
        if(lines.length > 1){
            //iterate through lines and see the one that starts with host
            //then extract info from it
        }
        else{

        }

    }

    @Override
    public byte[] toByteArray() {
        return rawRequest;
    }
}
