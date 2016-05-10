package pao.mdoru.utils;

/**
 * Created by m-doru on 10.05.2016.
 */
public class HttpProxyLogger extends Logger {
    private static final String FILENAME = "httpProxy.log";
    private static final String DIRNAME = "httpProxy";

    public HttpProxyLogger(String destinationDirectoryName) {
        super(destinationDirectoryName, FILENAME);
    }

    public HttpProxyLogger(){
        super(DIRNAME, FILENAME);
    }
}
