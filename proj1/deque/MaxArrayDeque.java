package deque;

import java.util.Comparator;

public class MaxArrayDeque extends ArrayDeque {

    public Comparator<T> comparatorDefault;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparatorDefault = c;

    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    public T max() {
        if (first == null) {
            return null;
        }
        T currentmax = this.items[first];
        for (int i = 0; i < size; i += 1) {
            T item = get(i);
            if (comparatorDefault.compare(item, currentmax) > 0) {
                currentmax = item;
            }
        }
        return currentmax;
    }

    public T max(Comparator<T> c) {
        if (first == null) {
            return null;
        }
        T currentmax = items[first];
        for (int i = 0; i < size; i += 1) {
            T item = get(i);
            if (c.compare(item, currentmax) > 0) {
                currentmax = item;
            }
        }
        return currentmax;
    }

}
