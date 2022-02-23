package deque;

public class ArrayDeque<T> {
    private T[] items;
    private Integer nextFirst;
    private Integer nextLast;
    private int size;

    /** Creates an empty deque */
    public ArrayDeque() {
        items = (T[]) new Object[8];
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
        System.arraycopy(items, 0, newItems, 0, size);
        nextFirst = newItems.length - 1;
        nextLast = size;
        items = newItems;
    }

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
        if (nextFirst == null && nextLast == null) {
            resize();
        }
        items[nextFirst] = item;
        size += 1;
        if (size == items.length) {
            nextFirst = null;
            nextLast = null;
        } else {
            if (checkIndexLoop()) {
                nextFirst = items.length - 1;
            } else {
                nextFirst -= 1;
            }
        }
    }

    public void addLast(T item) {
        if (nextFirst == null && nextLast == null) {
            resize();
        }
        items[nextLast] = item;
        size += 1;
        if (size == items.length) {
            nextFirst = null;
            nextLast = null;
        } else {
            if (checkIndexLoop()) {
                nextLast = 0;
            } else {
                nextLast += 1;
            }
        }
    }

    public static void main(String[] args) {
        ArrayDeque A = new ArrayDeque();
        ArrayDeque B = new ArrayDeque(1);
        A.addFirst(2);
        A.addFirst(3);
        A.addFirst(4);
        A.addFirst(5);
        A.addFirst(6);
        A.addFirst(7);
        A.addFirst(8);
        A.addFirst(9);
        A.addFirst(10);
    }

}
