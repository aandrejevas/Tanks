package Tank_Game.Patterns.Iterator;

public interface TIterator<T, V> {
    boolean hasNext();
    V next();
    void reset();

    T currentKey();
    V currentValue();
}
