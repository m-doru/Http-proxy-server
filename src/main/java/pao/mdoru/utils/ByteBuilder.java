package pao.mdoru.utils;

/**
 * Created by m-doru on 28.04.2016.
 */


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class ByteBuilder {
    private int size = 0;
    private byte[] buffer = new byte[1024];

    /**
     * Appends the byte array
     * @param buf
     */
    public void append(byte[] buf){
        if(null == buf)
            return;

        if(buf.length + this.size > this.buffer.length){
            this.buffer = Arrays.copyOf(this.buffer, 2*this.buffer.length);
        }

        System.arraycopy(buf, 0, this.buffer, this.size, buf.length);

        this.size += buf.length;
    }

    /**
     * Appends first length bytes from buf
     * @param buf
     * @param length
     */
    public void append(byte[] buf, int length){
        if(null == buf)
            return;
        if(length > buf.length)
            throw new IllegalArgumentException("length parameter value is bigger than buf size");

        if(length + this.size > this.buffer.length)
            this.buffer = Arrays.copyOf(this.buffer, 2*this.buffer.length);

        System.arraycopy(buf, 0, this.buffer, this.size, length);

        this.size += length;
    }

    public byte[] toArray(){
        return Arrays.copyOfRange(this.buffer, 0, size);
    }
}
