package pao.mdoru.impl;


import pao.mdoru.utils.ServerLogger;

import java.io.*;
import java.net.*;

/**
 * Created by m-doru on 21.04.2016.
 */
public class HttpRequestHandler {
    private final byte[] NEW_LINE = {'\r', '\n'};
    private Socket clientSocket;

    public HttpRequestHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void handle(HttpRequestHeader header) throws IOException {
        if(header == null)
            throw new IllegalArgumentException("header is null");
        Socket server = this.handleRequest(header);

        if(server != null)
            this.handleServerResponse(server);
        this.clientSocket.close();
    }

    private Socket handleRequest(HttpRequestHeader request) {
        try {
            Socket server = this.getServerConnection(request);

            OutputStream serverWriter = server.getOutputStream();

            serverWriter.write((request.getType() + " " + request.getUrl() + " " + request.getVersion()).getBytes());
            serverWriter.write(NEW_LINE);

            request.getParameters()
                    .forEach((k, v) -> {
                        try {
                            serverWriter.write((k + ": " + v).getBytes());
                            serverWriter.write(NEW_LINE);
                        } catch (IOException e) {}
                    });

            serverWriter.write(NEW_LINE);

            if(request.hasContent()){

                this.processRequestBody(this.clientSocket.getInputStream(), serverWriter, request.getContentLength());

            }

            return server;
        } catch (URISyntaxException e) {
        } catch (IOException e) {
        } catch (NullPointerException e){
        }
        return null;
    }

    private Socket getServerConnection(HttpRequestHeader request) throws URISyntaxException, IOException {
        URI destination = new URI(request.getUrl());
        int port = destination.getPort() < 0 ? 80 : destination.getPort();
        if(port == 80)
            port = request.getPort() == 0 ? 80 : request.getPort();

        return new Socket(destination.getHost(), port);
    }

    private void processRequestBody(InputStream requestBody, OutputStream serverWriter, int bodyLength) throws IOException {

        int read;

        byte[] buffer = new byte[8192];

        while(bodyLength > 0){
            read = requestBody.read(buffer);

            if(read < 0)
                break;

            serverWriter.write(buffer, 0, Math.min(read, bodyLength));

            bodyLength -= read;
        }
    }

    private void handleServerResponse(Socket server) throws IOException {
        HttpRequestAnswerHeader answerHeader = new HttpRequestAnswerHeader();

        answerHeader.parseHeader(server.getInputStream());

        answerHeader.addParameter("Proxy-Connection: Close");

        OutputStream clientWriter = this.clientSocket.getOutputStream();

        ServerLogger logger = new ServerLogger("server");


        for(String line : answerHeader.getParameters()){
            logger.log(line);
            logger.log("\n");

            clientWriter.write(line.getBytes("UTF-8"));
            clientWriter.write(NEW_LINE);
        }
        clientWriter.write(NEW_LINE);
        logger.log("\n");
        logger.flush();

        byte[] buffer = new byte[8192];

        int read;

        InputStream serverReader = server.getInputStream();

        while(true){
            read = serverReader.read(buffer);

            if(read < 0)
                break;

            clientWriter.write(buffer, 0, read);
        }
    }

}
