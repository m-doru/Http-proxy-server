package pao.mdoru.interfaces;

/**
 * Created by m-doru on 21.04.2016.
 */
public interface Request {
    void deserialize(byte[] buffer);
    byte[] serialize();
}
