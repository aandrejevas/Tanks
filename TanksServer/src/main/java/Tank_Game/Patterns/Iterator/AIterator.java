package Tank_Game.Patterns.Iterator;

public interface AIterator<T> {
    boolean hasNext();
    T next();
    void reset();

    T value();
}
