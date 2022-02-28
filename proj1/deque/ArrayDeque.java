package deque;

import edu.princeton.cs.algs4.StdRandom;

import static org.junit.Assert.assertTrue;

public class ArrayDeque<T> {
    public T[] items;
    public Integer nextFirst;
    public Integer nextLast;
    public Integer first;
    public Integer last;
    public int size;

    /** Creates an empty deque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        first = null;
        nextLast = 1;
        last = null;
        size = 0;
    }

    /** Creates deque of one item */
    public ArrayDeque(T item) {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        items[nextFirst] = item;
        first = nextFirst;
        last = nextFirst;
        nextFirst = (items.length - 1);
        size = 1;
    }

    /** ADD FIRST */
    public void addFirst(T item) {
        /** resize check before add First */
        if (size == items.length) {
            resize();
        }
        /** add item to first position */
        items[nextFirst] = item;

        /** update first */
        first = nextFirst;

        /** update last */
        if (last == null) {
            last = nextFirst;
        } else {
            if (nextLast == 0) {
                last = (items.length - 1);
            } else {
                last = nextLast - 1;
            }
        }
        /** update nextFirst */
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
    public void addLast(T item) {
        /** resize check before add Last */
        if (size == items.length) {
            resize();
        }
        /** add item to last position */
        items[nextLast] = item;

        /** update Last */
        last = nextLast;

        /** update first */
        if (first == null) {
            first = last;
        }

        /** update nextLast */
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
            if (first == (items.length - 1)){
                first = 0;
            } else {
                first += 1;
            }
        }

        return item;
    }

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
            if (last == 0){
                last = (items.length - 1);
            } else {
                last -= 1;
            }
        }

        return item;
    }

    /** GET */
    public T get(int n) {
        if (size() == 0) {
            return null;
        } else {
            int index = ((first + n) % items.length);
            return items[index];
        }
    }

    /** PRINT */
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

    public void resize() {
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

    /** HELPERS */
    /* resets first and last if the whole array becomes empty */
    public void resetFirstAndLast() {
        if (size == 0) {
            nextFirst = 0;
            nextLast = 1;
        }
    }

    /* Check if remove brings memory storage under 25%, if so, call downsize */
    public void capacityCheck() {
        if (items.length >= 16 && ((size - 1) < items.length *.25)) {
            downsize();
        }
    }

    /* Reduces length of array to half and resets the nextFirst and nextLast */
    public void downsize() {
        T[] newItems = (T[]) new Object [items.length / 2];
        if (nextLast == 0) {
            System.arraycopy(items, first, newItems, 0, size);
        } else if (last < first) {
            System.arraycopy(items, first, newItems, 0, (items.length - first));
            System.arraycopy(items, 0, newItems, (items.length - first), (size - (items.length - first)));
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

    /** EMPTY AND SIZE */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    /** MAIN */
    public static void main(String[] args) {
        ArrayDeque<Integer> L = new ArrayDeque<Integer>();

    }

}
