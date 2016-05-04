package pao.mdoru.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * Created by m-doru on 02.05.2016.
 */
public class LineInputStreamTest {
    InputStream stream;
    @Before
    public void setUp() throws Exception {

        new Thread(
                () -> {
                    ServerSocket ss = null;
                    try {
                        ss = new ServerSocket(32165);

                        Socket s = ss.accept();

                        this.stream = s.getInputStream();

                        ss.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }
    private Socket connect(String address, int port) throws IOException {
        return  new Socket(address, port);
    }
    @After
    public void tearDown(){
        try {
            if(this.stream != null)
                this.stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void readLine_MultipleLineContainingStream_CorrectlyReturnsTheLines() throws Exception {
        Thread.sleep(100);

        this.connect("localhost", 32165).getOutputStream().write((
                "this is a test\n" +
                        "another line\n" +
                        "last line\n" +
                        "\n"
        ).getBytes());

        String line1 = LineInputStream.readLine(this.stream);
        String line2 = LineInputStream.readLine(this.stream);
        String line3 = LineInputStream.readLine(this.stream);
        String emptyLine = LineInputStream.readLine(this.stream);

        assertEquals("this is a test", line1);
        assertEquals("another line", line2);
        assertEquals("last line", line3);
        assertEquals("", emptyLine);
    }

}