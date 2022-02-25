package deque;

public class ArrayDeque<T> {
    public T[] items;
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
        } else {
            first = nextFirst + 1;
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

        /* save item to return at end */
        T item = items[first];

        /* remove the item */
        items[first] = null;

        /* update size */
        size -= 1;

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


//        /* check memory and update */
//        capacityCheck();
//        resetFirstAndLast();
        return item;
    }

    public T removeLast() {
        /* empty list check */
        if (size == 0) {
            return null;
        }

        /* save item to return at end */
        T item = items[last];

        /* remove the item */
        items[last] = null;

        /* update size */
        size -= 1;

        /* update nextLast */
        nextLast = last;

        /* update nextLast */
        if (nextFirst == null) {
            nextFirst = nextLast;
        }

        /* update first */
        if (last == 0){
            last = (items.length - 1);
        } else {
            last -= 1;
        }

        return item;
    }

    /** GET */
    public T get(int n) {
        if (size() == 0) {
            return null;
        } else {
            int index;
            if (first == 0) {
                index = n;
            } else {
                index = ((first + n) % items.length);
            }
            return items[index];
        }
    }

    /** PRINT */
    public void printDeque() {
        int p;
        if (nextFirst == 0) {
            p = 0;
        } else {
            p = nextFirst + 1;
        }
        int s = size;
        while (s != 0) {
            System.out.print(items[p] + " ");
            if (p == items.length - 1) {
                p = 0;
            } else {
                p += 1;
            }
            s -= 1;
        }
        System.out.println();
    }

    public void resize() {
        T[] newItems = (T[]) new Object [items.length * 2];
        System.arraycopy(items, 0, newItems, 0, size);
        nextFirst = newItems.length - 1;
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
        if (items.length >= 16 && (size < items.length *.25)) {
            downsize();
        }
    }

    /* Reduces length of array to half and resets the nextFirst and nextLast */
    public void downsize() {
        T[] newItems = (T[]) new Object [items.length / 3];
        if (nextLast == 0) {
            System.arraycopy(items, (nextFirst + 1), newItems, 0, size);
        } else if (nextLast < nextFirst) {
            System.arraycopy(items, (nextFirst + 1), newItems, 0, (items.length - 1 - nextFirst));
            System.arraycopy(items, 0, newItems, (items.length - nextFirst), nextLast);
        } else {
            System.arraycopy(items, (nextFirst + 1), newItems, 0, size);
        }
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

    /* size check */
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        ArrayDeque A = new ArrayDeque();
        A.addLast(2);
        A.addLast(3);
        A.addLast(4);
        A.addLast(5);
        A.addLast(6);
        A.addLast(7);
        A.addLast(8);
        A.addLast(9);
        A.addLast(10);
        A.removeLast();

    }

}
