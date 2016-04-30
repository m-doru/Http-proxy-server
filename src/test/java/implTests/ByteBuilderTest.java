package implTests;

import org.junit.Test;
import pao.mdoru.utils.ByteBuilder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


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

        assertArrayEquals(fullAppended, builder.toArray());

    }

    @Test
    public void append_nullArray_DoesNothing(){
        ByteBuilder builder = new ByteBuilder();
        byte[] appended = "test".getBytes();
        builder.append(appended);

        builder.append(null);

        assertArrayEquals(appended, builder.toArray());
    }

    @Test
    public void append2_ValidParameters_CorrectlyHandlesThem(){
        ByteBuilder builder = new ByteBuilder();

        byte[] fullAppended = "test1test2test3".getBytes();

        builder.append("test1DONOTAPPENDTHIS".getBytes(), 5);
        builder.append("test2DONOTAPPENDTHIS".getBytes(), 5);
        builder.append("test3DONOTAPPENDTHIS".getBytes(), 5);

        assertArrayEquals(fullAppended, builder.toArray());
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

        assertTrue(threwException);
    }
    @Test
    public void append_heavyWorload(){
        byte[] source = "test test test".getBytes();

        ByteBuilder builder = new ByteBuilder();

        for(int i = 0; i < 1000000; ++i)
            builder.append(source);
    }
}