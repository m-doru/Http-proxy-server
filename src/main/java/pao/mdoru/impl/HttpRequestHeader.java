package pao.mdoru.impl;

import pao.mdoru.interfaces.Request;

import java.util.Hashtable;

/**
 * Created by m-doru on 21.04.2016.
 */
public class HttpRequestHeader implements Request {
    private byte[] content;
    private int port;
    private String host;
    private String type;
    private String url;
    private String version;
    private int contentLength = 0;
    private Hashtable<String, String> parameters = new Hashtable<>();

    public HttpRequestHeader(byte[] requestHeader) throws IllegalArgumentException{
        this.content = requestHeader;
        this.deserialize(new String(requestHeader));
    }

    public HttpRequestHeader(String requestHeader) throws IllegalArgumentException{
        this.content = requestHeader.getBytes();
        this.deserialize(requestHeader);
    }

    public void deserialize(byte[] buffer) throws IllegalArgumentException{
        this.deserialize(new String(buffer));
    }

    public void deserialize(String requestHeader) throws IllegalArgumentException{
        String[] lines = requestHeader.split("\\r?\\n");
        if(lines.length == 0)
            return;

        this.processFirstLine(lines[0]);

        for(int i = 1; i < lines.length; ++i){
            String[] parameterValue = lines[i].split(": ");


            if(parameterValue.length == 2) {
                this.parameters.put(parameterValue[0], parameterValue[1]);

                if(parameterValue[0].equalsIgnoreCase("host"))
                    this.processHost(parameterValue[1]);

                if (parameterValue[0].equalsIgnoreCase("content-length"))
                    this.contentLength = Integer.parseInt(parameterValue[1]);
            }
        }
    }

    private void processHost(String host){
        String[] hostPort = host.split(":");
        if(hostPort.length == 2){
            this.host = hostPort[0];
            this.port = Integer.parseInt(hostPort[1]);
        }
        else{
            this.host = hostPort[0];
            this.port = 80;
        }
    }

    private void processFirstLine(String line) throws IllegalArgumentException {
        String[] data = line.split(" ");

        for(String field : data){
            if(field.startsWith("http") || (!field.startsWith("HTTP") && field.contains("/")))
                this.url = field;

            if(field.equals("GET") || field.equals("POST") || field.equals("UPDATE"))
                this.type = field;

            if(field.startsWith("HTTP/1."))
                this.version = field;
        }
    }

    public byte[] serialize() {
        return this.content;
    }

    public boolean hasContent(){ return this.contentLength > 0; }

    public int getContentLength(){return contentLength;}

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getType() { return this.type; }

    public String getUrl(){ return this.url; }

    public String getVersion(){ return this.version; }

    public Hashtable<String, String> getParameters(){ return this.parameters; }
}
