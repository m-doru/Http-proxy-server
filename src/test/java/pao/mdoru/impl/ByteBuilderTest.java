package pao.mdoru.impl;

import pao.mdoru.utils.ByteBuilder;

import org.junit.Test;
import org.junit.Assert;


/**
 * Created by m-doru on 28.04.2016.
 */
public class ByteBuilderTest {
    @Test
    public void append_validArrays_CorrectlyAppendsThem(){
        ByteBuilder builder = new ByteBuilder();
        byte[] fullAppended = "test1test2test3".getBytes();
        builder.append("test1".getBytes());
        builder.append("test2".getBytes());
        builder.append("test3".getBytes());

        Assert.assertArrayEquals(fullAppended, builder.toArray());
    }

    @Test
    public void append_nullArray_DoesNothing(){
        ByteBuilder builder = new ByteBuilder();
        byte[] appended = "test".getBytes();
        builder.append(appended);

        builder.append(null);

        Assert.assertArrayEquals(appended, builder.toArray());
    }

    @Test
    public void append2_ValidParameters_CorrectlyHandlesThem(){
        ByteBuilder builder = new ByteBuilder();

        byte[] fullAppended = "test1test2test3".getBytes();

        builder.append("test1DONOTAPPENDTHIS".getBytes(), 5);
        builder.append("test2DONOTAPPENDTHIS".getBytes(), 5);
        builder.append("test3DONOTAPPENDTHIS".getBytes(), 5);

        Assert.assertArrayEquals(fullAppended, builder.toArray());
    }

    @Test
    public void append2_LengthBiggerThanArrayLength_ThrowsIllegalArgumentException(){
        ByteBuilder builder = new ByteBuilder();

        boolean threwException = false;
        try {
            builder.append("test".getBytes(), 5);
        }
        catch (IllegalArgumentException e){
            threwException = true;
        }

        Assert.assertTrue(threwException);
    }
}