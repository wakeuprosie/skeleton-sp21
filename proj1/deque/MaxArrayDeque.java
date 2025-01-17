package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> comparatorDefault;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparatorDefault = c;
    }

    public T max() {
        if (get(0) == null) {
            return null;
        }
        T currentMax = get(0);
        for (int i = 0; i < size(); i += 1) {
            T item = get(i);
            if (comparatorDefault.compare(item, currentMax) > 0) {
                currentMax = item;
            }
        }
        return currentMax;
    }

    public T max(Comparator<T> c) {
        if (get(0) == null) {
            return null;
        }
        T currentMax = get(0);
        for (int i = 0; i < size(); i += 1) {
            T item = get(i);
            if (c.compare(item, currentMax) > 0) {
                currentMax = item;
            }
        }
        return currentMax;
    }

}
