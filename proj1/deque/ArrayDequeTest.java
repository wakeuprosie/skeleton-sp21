package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/** Performs tests on each method of class Array Deque */
public class ArrayDequeTest {

    /* Check if addFirst works */
    @Test
    public void addFirstTest() {
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

    @Test
    public void addLastTest() {
        ArrayDeque A = new ArrayDeque();
        ArrayDeque B = new ArrayDeque(1);
        A.addLast(2);
        A.addLast(3);
        A.addLast(4);
        A.addLast(5);
        A.addLast(6);
        A.addLast(7);
        A.addLast(8);
        A.addLast(9);
        A.addLast(10);
    }

    @Test
    public void printDequeTest() {
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
        A.printDeque();
    }

    @Test
    public void removeFirstTest() {
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
        A.removeFirst();
        A.size();
    }

    @Test
    public void removeLastTest() {
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

    @Test
    public void getTest() {
        ArrayDeque A = new ArrayDeque();
        A.addLast(2);
        A.addLast(3);
        A.addLast(4);
        assertEquals(A.get(5), 2);
        assertEquals(A.get(2), null);
        assertEquals(A.get(19), null);
    }

    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> L = new ArrayDeque<Integer>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addFirst
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
            } else if (operationNumber == 1) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
            } else if (operationNumber == 2) {
                // removeFirst
                if (L.size() > 0) {
                    L.removeFirst();
                }
            } else if (operationNumber == 3) {
                // removeLast
                if (L.size() > 0) {
                    L.removeLast();
                }
            }
        }
    }


    @Test
    public void downsizeTest() {
        ArrayDeque A = new ArrayDeque();
        for (int i = 0; i < 20; i += 1) {
            A.addFirst(i);
        }
        A.removeFirst();
    }

}
