package deque;

/** Double linked list class **/
public class LinkedListDeque<T> {
    private class TNode {
        public T item;
        public TNode prev;
        public TNode next;

        public TNode(T i, TNode p, TNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    /* First item if exists, is at sentinel.next */
    private TNode sentinel;
    private TNode last;
    private int size;

    /* Creates an empty Linked List Deque */
    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        last = new TNode(null, sentinel, null);
        sentinel.next = last;
        size = 0;
    }

    /* Creates a one element Linked List Deque */
    public LinkedListDeque(T x) {
        sentinel = new TNode(x, null, null);
        last = new TNode(x, null, null);
        sentinel.next = new TNode(x, sentinel, last);
        last.prev = sentinel.next;
        size = 1;
    }

    /* Adds x to the front of the list */
    public void addFirst(T item) {
        TNode pointer_hold = sentinel.next;
        sentinel.next = new TNode(item, sentinel, pointer_hold);
        pointer_hold.prev = sentinel.next;
        size += 1;
    }

    /* Adds x to the back of the list */
    public void addLast(T item) {
        TNode news = new TNode(item, last.prev, last);
        last.prev = news;
        news.prev.next = news;
        size += 1;
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
        if (sentinel.next != last) {
            TNode p = sentinel.next;
            System.out.print(p.item + " ");
            while (p.next != last) {
                p = p.next;
                System.out.print(p.item + " ");
            }
        } else {
            System.out.print("Empty Deque");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (sentinel.next == last) {
            return null;
        } else {
            T item = sentinel.next.item;
            TNode hold = sentinel.next.next;
            sentinel.next = sentinel.next.next;
            hold.prev = sentinel;
            size -= 1;
            return item;
        }
    }

    public T removeLast() {
        if (last.prev == sentinel) {
            return null;
        } else {
            T item = last.prev.item;
            TNode hold = last.prev.prev;
            last.prev = last.prev.prev;
            hold.next = last;
            size -= 1;
            return item;
        }
    }

    public T get(int index) {
        if (sentinel.next == last) {
            return null;
        } else {
            TNode p = sentinel.next;
            while (index != 0) {
                p = p.next;
                index -= 1;
            }
            return p.item;
        }
    }

    public T getRecursive(int index) {
        if (sentinel.next == last) {
            return null;
        }
        TNode p = sentinel.next;
        if (index == 0) {
            return p.item;
        } else {
            p = p.next;
            return getRecursive(index - 1);
        }
    }

    /* Creates a linked list deque of one integer, 10 */
    public static void main(String[] args) {
        LinkedListDeque L = new LinkedListDeque();
        L.addLast(3);
        L.addLast(4);
        L.addLast(5);
        L.removeLast();
        L.printDeque();
    }

}
