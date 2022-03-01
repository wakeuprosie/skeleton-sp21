package deque;

import java.util.Iterator;

/** Double linked list class **/
public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class TNode {
        private T item;
        private TNode prev;
        private TNode next;

        TNode(T i, TNode p, TNode n) {
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

    /* Adds x to the front of the list */
    @Override
    public void addFirst(T item) {
        TNode pointerHold = sentinel.next;
        sentinel.next = new TNode(item, sentinel, pointerHold);
        pointerHold.prev = sentinel.next;
        size += 1;
    }

    /* Adds x to the back of the list */
    @Override
    public void addLast(T item) {
        TNode news = new TNode(item, last.prev, last);
        last.prev = news;
        news.prev.next = news;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    private T getHelper(TNode p, int index) {
        if (index == 0) {
            return p.item;
        } else {
            return getHelper(p.next, index - 1);
        }
    }

    public T getRecursive(int index) {
        /* empty LL */
        if (sentinel.next == last) {
            return null;
        }
        TNode currentNode = sentinel.next;

        if (index == 0)  {
            return currentNode.item;
        } else return getHelper(currentNode.next, index - 1);
    }

    /** ITERATOR */
    /* New class of iterator */
    private class LinkedListIterator implements Iterator<T> {
        private int wizPos;

        LinkedListIterator() {
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
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /* Equals */
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
            if (!(this.get(i).equals(object.get(i)))) {
                return false;
            }
        }
        return true;
    }
}
