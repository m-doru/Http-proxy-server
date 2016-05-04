package pao.mdoru.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by m-doru on 30.04.2016.
 */
public class DefaultClientHandlerTest {
    ServerSocket serverSocket;
    Socket connection;
    Logger logger = Logger.getLogger(DefaultClientHandler.class.getName());
    String httpPOSTRequest = "POST infoarena.ro/comment HTTP/1.1\n" +
            "Host: infoarena.ro\n" +
            "Keep-Alive: timeout=15\n" +
            "Connection: Keep-Alive\n" +
            "Content-Length: 4\n" +
            "\n" +
            "test";
    String httpPostRequestHeader = "POST infoarena.ro/comment HTTP/1.1\n" +
            "Host: infoarena.ro\n" +
            "Keep-Alive: timeout=15\n" +
            "Connection: Keep-Alive\n" +
            "Content-Length: 4\n" +
            "\n";
    String httpPostRequestContent = "test";

    String httpGETRequest = "GET / HTTP/1.1\n" +
            "Host: localhost:32165\n" +
            "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
            "Accept-Language: en-US,en;q=0.5\n" +
            "Accept-Encoding: gzip, deflate\n" +
            "Connection: keep-alive\n"+
            "\n";

    public DefaultClientHandlerTest() throws IOException {
        try {
            this.serverSocket = new ServerSocket(32165);
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Failed to open server socket on port 32165");
            throw e;
        }
    }

    @Before
    public void setUp() throws IOException {
        try {
            this.connection = new Socket("localhost", 32165);
        } catch (IOException e) {
            this.logger.log(Level.SEVERE, "Failed to connect socket to server socket");
            throw e;
        }
    }

    @After
    public void tearDown(){
        try {
            this.connection.close();
            this.serverSocket.close();
        } catch (IOException e) {
            this.logger.log(Level.WARNING, "Failed to close connection to server socket");
        }
    }
    @Test
    public void readHeader_PostRequest_CorrectlyReadsRequestHeader(){
        try (Socket client = this.serverSocket.accept();){
            this.connection.getOutputStream().write(this.httpPOSTRequest.getBytes());

            DefaultClientHandler handler = new DefaultClientHandler(client);

            Method readRequestHeader = handler.getClass().getDeclaredMethod("readHeader");

            readRequestHeader.setAccessible(true);

            Object requestHeader = readRequestHeader.invoke(handler);

            Assert.assertEquals(this.httpPostRequestHeader, (String)requestHeader);
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.log(Level.SEVERE, "Failed to accept connection");
            Assert.assertTrue(false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            this.logger.log(Level.SEVERE, "Got the readHeader method name wrong");
            Assert.assertTrue(false);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    @Test
    public void readContent_PostRequest_CorrectlyReadsContent(){
        try(Socket connection = this.serverSocket.accept()){
            this.connection.getOutputStream().write(this.httpPOSTRequest.getBytes());

            DefaultClientHandler handler = new DefaultClientHandler(connection);

            Method readRequestHeader = DefaultClientHandler.class.getDeclaredMethod("readHeader");

            readRequestHeader.setAccessible(true);

            readRequestHeader.invoke(handler);
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.log(Level.SEVERE, "Failed to accept connection");
            Assert.assertTrue(false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    @Test
    public void readHeader_GETRequest_CorrectlyReadsRequest(){
        try (Socket client = this.serverSocket.accept();){
            this.connection.getOutputStream().write(this.httpGETRequest.getBytes());

            DefaultClientHandler handler = new DefaultClientHandler(client);

            Method readRequestHeader = handler.getClass().getDeclaredMethod("readHeader");

            readRequestHeader.setAccessible(true);

            Object requestHeader = readRequestHeader.invoke(handler);

            Assert.assertEquals(this.httpGETRequest, (String)requestHeader);
        } catch (IOException e) {
            e.printStackTrace();
            this.logger.log(Level.SEVERE, "Failed to accept connection");
            Assert.assertTrue(false);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            this.logger.log(Level.SEVERE, "Got the readHeader method name wrong");
            Assert.assertTrue(false);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
}
