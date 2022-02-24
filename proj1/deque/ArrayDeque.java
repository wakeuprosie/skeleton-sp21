package deque;

public class ArrayDeque<T> {
    private T[] items;
    private Integer nextFirst;
    private Integer nextLast;
    private int size;

    /** Creates an empty deque */
    public ArrayDeque() {
        items = (T[]) new Object[100];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    /** Creates a deque of one item */
    public ArrayDeque(T item) {
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        items[nextFirst + 1] = item;
        if (nextLast == items.length) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size = 1;
    }

    public void resize() {
        T[] newItems = (T[]) new Object [items.length * 2];
        System.arraycopy(items, 0, newItems, 0, nextFirst);
        System.arraycopy(items, (nextLast + 1), newItems, (items.length + nextFirst + 1), (items.length - nextFirst - 1));
        nextFirst = items.length + nextFirst;
        items = newItems;
    }

    /* Check if remove brings memory storage under 25%, if so, call downsize */
    public void capacityCheck() {
        if (items.length >= 100 && size < items.length *.25) {
            downsize();
        }
    }

    /* Reduces length of array to half and resets the nextFirst and nextLast */
    public void downsize() {
        T[] newItems = (T[]) new Object [items.length / 2];
        if (nextLast < nextFirst) {
            System.arraycopy(items, (nextFirst + 1), newItems, 0, (items.length - (nextFirst + 1)));
            System.arraycopy(items, 0, newItems, (items.length - nextFirst - 1), (nextLast - 1));
        } else {
            System.arraycopy(items, (nextFirst + 1), newItems, 0, size);
        }
        nextFirst = 0;
        nextLast = 1;
        items = newItems;
    }

    /* Checks if incrementing nextFirst or nextLast will error */
    public boolean checkIndexLoop() {
        if (nextFirst == 0) {
            return true;
        } else if (nextLast == items.length - 1) {
            return true;
        } else {
            return false;
        }
    }

    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            resize();
        }
        items[nextFirst] = item;
        size += 1;
        if (checkIndexLoop()) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
    }

    public void addLast(T item) {
        if (nextFirst == nextLast) {
            resize();
        }
        items[nextLast] = item;
        size += 1;
        if (checkIndexLoop()) {
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
        if (checkIndexLoop()) {
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
        if (size == 0) {
            return null;
        }
        T item = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        if (nextFirst + 1 > items.length) {
            nextFirst = 0;
        } else {
            nextFirst = nextFirst + 1;
        }
        size -= 1;
        capacityCheck();
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item;
        if (nextLast == 0) {
            item = items[items.length];
            items[items.length] = null;
            nextLast = items.length;
        } else {
            item = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast -= 1;
        }
        size -= 1;
        capacityCheck();
        return item;
    }

    public T get(int index) {
        if (index > items.length) {
            System.out.println("Index out of range.");
            return null;
        } else if (items[index] == null) {
            System.out.println("Index item null.");
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
