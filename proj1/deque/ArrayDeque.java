package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private Integer nextFirst;
    private Integer nextLast;
    private Integer first;
    private Integer last;
    private int size;

    /** Creates an empty deque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        first = null;
        nextLast = 1;
        last = null;
        size = 0;
    }

    /* ADD FIRST */
    public void addFirst(T item) {
        /* resize check before add First */
        if (size == items.length) {
            resize();
        }
        /* add item to first position */
        items[nextFirst] = item;

        /* update first */
        first = nextFirst;

        /* update last */
        if (last == null) {
            last = nextFirst;
        } else {
            if (nextLast == 0) {
                last = (items.length - 1);
            } else {
                last = nextLast - 1;
            }
        }
        /* update nextFirst */
        if (nextFirst == 0) {
            nextFirst = (items.length - 1);
        } else {
            nextFirst -= 1;
        }
        size += 1;
        if (size == items.length) {
            nextFirst = null;
            nextLast = null;
        }
    }

    /* add last function */
    @Override
    public void addLast(T item) {
        /* resize check before add Last */
        if (size == items.length) {
            resize();
        }
        /* add item to last position */
        items[nextLast] = item;

        /* update Last */
        last = nextLast;

        /* update first */
        if (first == null) {
            first = last;
        }

        /* update nextLast */
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
        if (size == items.length) {
            nextLast = null;
            nextFirst = null;
        }
    }

    /** REMOVE FUNCTIONS */
    @Override
    public T removeFirst() {
        /* empty list check */
        if (size == 0) {
            return null;
        }

        /* capacity check */
        capacityCheck();

        /* save item to return at end */
        T item = items[first];

        /* remove the item */
        items[first] = null;

        /* update size */
        size -= 1;

        if (size == 0) {
            first = null;
            last = null;
            nextFirst = 0;
            nextLast = 1;
        } else {
            /* update nextFirst */
            nextFirst = first;

            /* update nextLast */
            if (nextLast == null) {
                nextLast = nextFirst;
            }

            /* update first */
            if (first == (items.length - 1)) {
                first = 0;
            } else {
                first += 1;
            }
        }

        return item;
    }

    @Override
    public T removeLast() {
        /* empty list check */
        if (size == 0) {
            return null;
        }

        /* capacity check */
        capacityCheck();

        /* save item to return at end */
        T item = items[last];

        /* remove the item */
        items[last] = null;

        /* update size */
        size -= 1;

        if (size == 0) {
            first = null;
            last = null;
            nextFirst = 0;
            nextLast = 1;

        } else {
            /* update nextLast */
            nextLast = last;

            /* update nextLast */
            if (nextFirst == null) {
                nextFirst = nextLast;
            }

            /* update last */
            if (last == 0) {
                last = (items.length - 1);
            } else {
                last -= 1;
            }
        }

        return item;
    }

    /** GET */
    @Override
    public T get(int n) {
        if (size() == 0) {
            return null;
        } else {
            int index = ((first + n) % items.length);
            return items[index];
        }
    }

    /* PRINT */
    @Override
    public void printDeque() {
        int p = first;
        int s = size;
        while (s != 0) {
            System.out.print(items[p] + " ");
            p = (p + 1) % items.length;
            s -= 1;
        }
        System.out.println();
    }

    private void resize() {
        T[] newItems = (T[]) new Object [items.length * 2];
        if (last < first) {
            System.arraycopy(items, first, newItems, 0, (items.length - first));
            System.arraycopy(items, 0, newItems, (items.length - first), (last + 1));
        } else {
            System.arraycopy(items, first, newItems, 0, size);
        }
        first = 0;
        last = size - 1;
        nextFirst = (newItems.length - 1);
        nextLast = size;

        items = newItems;
    }

    /* HELPERS */
    /* Check if remove brings memory storage under 25%, if so, call downsize */
    private void capacityCheck() {
        if (items.length >= 16 && ((size - 1) < items.length * .25)) {
            downsize();
        }
    }

    /* Reduces length of array to half and resets the nextFirst and nextLast */
    private void downsize() {
        T[] newItems = (T[]) new Object [items.length / 2];
        if (nextLast == 0) {
            System.arraycopy(items, first, newItems, 0, size);
        } else if (last < first) {
            System.arraycopy(items, first, newItems, 0, (items.length - first));
            System.arraycopy(items, 0, newItems,
                    (items.length - first), (size - (items.length - first)));
        } else {
            System.arraycopy(items, first, newItems, 0, size);
        }
        /* update tracker variables */
        first = 0;
        last = size - 1;
        nextFirst = newItems.length - 1;
        nextLast = size;
        items = newItems;
    }

    /* SIZE */
    @Override
    public int size() {
        return size;
    }

    /** ITERATOR */
    /* New class of iterator */
    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;

        ArrayDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    /* Returns an iterator */
    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    public boolean equals(Object o) {
        if (o == null || (!(o instanceof Deque))) {
            return false;
        }
       Deque object = (Deque) o;

        if (this == o) {
            return true;
        }
        if (this.size() != object.size()) {
            return false;
        }

        for (int i = 0; i < size(); i += 1) {
            if (this.get(i) != object.get(i)) {
                return false;
            }
        }
        return true;
    }


}
