package pao.mdoru.impl;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Created by m-doru on 30.04.2016.
 */
public class HttpRequestTest {
    private String httpGETRequestWithPort = "GET / HTTP/1.1\n" +
            "Host: localhost:32165\n" +
            "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
            "Accept-Language: en-US,en;q=0.5\n" +
            "Accept-Encoding: gzip, deflate\n" +
            "Connection: keep-alive\n"+
            "\n";

    private String httpGETRequest = "GET localhost/test HTTP/1.1\n" +
            "Host: localhost\n" +
            "User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
            "Accept-Language: en-US,en;q=0.5\n" +
            "Accept-Encoding: gzip, deflate\n" +
            "Connection: keep-alive\n"+
            "\n";

    private String httpPOSTRequest = "POST localhost/comment HTTP/1.1\n" +
            "Host: localhost\n" +
            "Keep-Alive: timeout=15\n" +
            "Connection: Keep-Alive\n" +
            "Content-Length: 4\n" +
            "\n";
    @Test
    public void deserialize_GETRequest_CorrectlyExtractsInfo() throws Exception {
        HttpRequestHeader request = new HttpRequestHeader(this.httpGETRequest);

        assertEquals("GET",request.getType());
        assertEquals("localhost",request.getHost());
        assertEquals(80, request.getPort());
        assertEquals(0, request.getContentLength());
        assertFalse(request.hasContent());

        Field httpVersion = HttpRequestHeader.class.getDeclaredField("version");
        httpVersion.setAccessible(true);

        assertEquals("HTTP/1.1", httpVersion.get(request));

        Field url = HttpRequestHeader.class.getDeclaredField("url");
        url.setAccessible(true);

        assertEquals("localhost/test", url.get(request));
    }

    @Test
    public void deserialize_GETRequestWithExplicitPort_CorrecltyExtractsPort(){
        HttpRequestHeader request = new HttpRequestHeader(this.httpGETRequestWithPort);

        assertEquals(32165, request.getPort());
    }

    @Test
    public void deserialize_POSTRequest_CorrectlyExtractsInfo(){
        HttpRequestHeader request = new HttpRequestHeader(this.httpPOSTRequest);

        assertEquals("POST", request.getType());
        assertTrue(request.hasContent());
        assertEquals(4, request.getContentLength());


    }

}