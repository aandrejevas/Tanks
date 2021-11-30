package Tank_Game.Patterns.Iterator;

import utils.Iterator.IteratorPtr;

public interface TIterator<T, V> extends IteratorPtr<V> {
    T currentKey();
    V currentValue();
}
