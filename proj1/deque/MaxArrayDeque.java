package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque {

    private Comparator<T> comparatorDefault;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparatorDefault = c;

    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    public T max() {
        if (first == null) {
            return null;
        }
        T currentmax = (T) items[first];
        for (int i = 0; i < size; i += 1) {
            T item = (T) get(i);
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
        T currentmax = (T) items[first];
        for (int i = 0; i < size; i += 1) {
            T item = (T) get(i);
            if (c.compare(item, currentmax) > 0) {
                currentmax = item;
            }
        }
        return currentmax;
    }

}
