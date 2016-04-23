package pao.mdoru.impl;

import pao.mdoru.interfaces.Request;

/**
 * Created by m-doru on 21.04.2016.
 */
public class HttpRequest implements Request {
    private byte[] rawRequest;
    private int port;
    private String host;
    private String type;

    public HttpRequest(byte[] buffer){
        this.deserialize(buffer);
    }

    @Override
    public void deserialize(byte[] buffer) {
        //TODO parse the buffer with the request and initialize the fields
        this.rawRequest = buffer;
        String stringRequest = new String(buffer);
        String[] lines = stringRequest.split("\\r?\\n");
        if(lines.length >= 1){
            //iterate through lines and extract the info from every one

        }
        else{

        }

    }

    @Override
    public byte[] serialize() {
        return rawRequest;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getType() {
        return this.type;
    }
}
