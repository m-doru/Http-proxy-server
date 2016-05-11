package pao.mdoru.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by m-doru on 02.05.2016.
 */
public class LineInputStream {
    public static String readLine(final InputStream stream) throws IOException {
        byte[] bit = new byte[1];

        ByteBuilder lineBuilder = new ByteBuilder();

        int read;

        while(true){
             read = stream.read(bit);

            if(read < 0)
                break;

            if(bit[0] == '\n')
                break;
            if(bit[0] != '\r')
            lineBuilder.append(bit);
        }

        if(read < 0 && lineBuilder.getSize() == 0)
            return null;

        return new String(lineBuilder.toArray());
    }
}
