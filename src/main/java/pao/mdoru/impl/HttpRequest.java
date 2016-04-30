package pao.mdoru.impl;

import pao.mdoru.interfaces.Request;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Hashtable;

/**
 * Created by m-doru on 21.04.2016.
 */
public class HttpRequest implements Request {
    private byte[] rawRequest;
    private int port;
    private String host;
    private String type;
    private String url;
    private byte[] content = null;
    private int contentLength = 0;
    private Hashtable<String, String> headerParameters = new Hashtable<>();

    public HttpRequest(byte[] buffer){
        this.deserialize(new String(buffer));
    }

    public HttpRequest(String request){ this.deserialize(request); }


    public void deserialize(byte[] buffer) {
        this.deserialize(new String(buffer));
    }

    public void deserialize(String request) {
        //TODO parse the buffer with the request and initialize the fields
        BufferedReader reader = new BufferedReader(new StringReader(request));

    }


    public byte[] serialize() {
        throw new UnsupportedOperationException();
    }

    public boolean hasContent(){ return this.contentLength > 0; }

    public int getContentLength(){return contentLength;}

    public void setContent(byte[] content){this.content = content;}

    public String getHost() {
        return this.host;
    }


    public int getPort() {
        return this.port;
    }


    public String getType() {
        return this.type;
    }
}
