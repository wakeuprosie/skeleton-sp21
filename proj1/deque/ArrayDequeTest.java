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
        A.addLast(2);
        A.addLast(3);
        A.addLast(4);
        A.addLast(5);
        A.addLast(6);
        A.addLast(7);
        A.addLast(8);
        A.addLast(9);
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
        A.printDeque();
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
        assertEquals(A.get(5), 7);
    }

    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> L = new ArrayDeque<Integer>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
            } else if (operationNumber == 1) {
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
            } else if (operationNumber == 2) {
                if (L.size() > 0) {
                    L.removeFirst();
                }
            } else if (operationNumber == 3) {
                if (L.size() > 0) {
                    L.removeLast();
                }
            }
        }
    }

    @Test
    public void resizeOnlyTest() {
        ArrayDeque<Integer> L = new ArrayDeque<Integer>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                // addFirst
                int randVal = StdRandom.uniform(0, 100);
                int S = L.size();
                L.addFirst(randVal);
                assertEquals(L.size(), S + 1);
            }
            if (operationNumber == 1) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                int S = L.size();
                L.addLast(randVal);
                assertEquals(L.size(), S + 1);
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
