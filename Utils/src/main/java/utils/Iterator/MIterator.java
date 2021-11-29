package utils.Iterator;

public interface MIterator <M>{
    boolean hasNext();
    M nextIJ();
    void reset();

    int keyI();
    int keyJ();
    M value();
}
