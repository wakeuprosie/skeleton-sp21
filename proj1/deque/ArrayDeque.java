package deque;

public class ArrayDeque<T> {
    public T[] items;
    private Integer nextFirst;
    private Integer nextLast;
    private int size;

    /** Creates an empty deque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    /** Creates a deque of one item */
    public ArrayDeque(T item) {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        items[nextFirst + 1] = item;
        if (nextLast == items.length) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size = 1;
    }

    /* resets first and last if the whole array becomes empty */
    public void resetFirstAndLast() {
        if (size == 0) {
            nextFirst = 0;
            nextLast = 1;
        }
    }

    public void resize() {
        T[] newItems = (T[]) new Object [items.length * 2];
        System.arraycopy(items, 0, newItems, 0, size);
        nextFirst = newItems.length - 1;
        nextLast = size;
        items = newItems;
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

    public void addFirst(T item) {
        items[nextFirst] = item;
        size += 1;
        if (size == items.length) {
            resize();
        } else if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
    }

    public void addLast(T item) {
        items[nextLast] = item;
        size += 1;
        if (size == items.length) {
            resize();
        } else if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
    }

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

    public T removeFirst() {
        /* empty list check */
        if (size == 0) {
            return null;
        }
        /* if no empty list ... */
        T item;
        int index;
        if (nextFirst == items.length - 1) {
           index = 0;
           nextFirst = 0;
        } else {
            index = nextFirst + 1;
            nextFirst += 1;
        }
        item = items[index];
        items[index] = null;
        size -= 1;
        capacityCheck();
        resetFirstAndLast();
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item;
        if (nextLast == 0) {
            item = items[items.length - 1];
            items[items.length - 1] = null;
            nextLast = items.length - 1;
        } else {
            item = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast -= 1;
        }
        size -= 1;
        capacityCheck();
        resetFirstAndLast();
        return item;
    }

    public T get(int index) {
        if (index > items.length) {
            return null;
        } else if (items[index] == null) {
            return null;
        } else {
            return items[index];
        }
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
