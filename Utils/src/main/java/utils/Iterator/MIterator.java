package utils.Iterator;

public interface MIterator <M>{
    boolean hasNext();
    M nextIJ();

    void reset();

    int keyI();
    int keyJ();
    M value();
    M value2();
    M valueReverseKey();
}
