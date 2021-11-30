package utils.Iterator;

public interface IteratorPtr <T>{
    boolean hasNext();
    T next();
    void reset();
}
