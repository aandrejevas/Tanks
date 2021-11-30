package Tank_Game.Patterns.Iterator;

import utils.Iterator.IteratorPtr;

public interface AIterator<T> extends IteratorPtr<T> {
    T value();
}
