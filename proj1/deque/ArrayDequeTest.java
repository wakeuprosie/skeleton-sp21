package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/** Performs tests on each method of class Array Deque */
public class ArrayDequeTest {

    @Test
    public void Test1(){
        ArrayDeque B = new ArrayDeque(1);
    }

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
        assertEquals(A.get(5), 7);
    }

    @Test
    public void fillEmptyFillTest() {
        ArrayDeque A = new ArrayDeque();
        A.addFirst(2);
        A.addLast(3);
        A.addLast(4);
        A.addLast(5);
        A.addLast(6);
        A.addLast(7);
        A.addLast(8);
        A.addLast(9);
        A.addLast(10);
        for (int i = 0; i < 8; i += 1) {
            A.removeFirst();
        }
        ArrayDeque B = new ArrayDeque();
        assertArrayEquals(A.items, B.items);
    }

    @Test
    public void removeLastTest() {
        ArrayDeque A = new ArrayDeque();
        ArrayDeque B = new ArrayDeque();
        B.addFirst(2);
        B.addLast(3);
        B.addLast(4);
        B.addLast(5);
        B.addLast(6);
        B.addLast(7);
        B.addLast(8);
        B.addLast(9);
        A.addFirst(2);
        A.addLast(3);
        A.addLast(4);
        A.addLast(5);
        A.addLast(6);
        A.addLast(7);
        A.addLast(8);
        A.addLast(9);
        A.addLast(10);
        A.removeLast();
        assertArrayEquals(A.items, B.items);
        assertEquals(A.get(0), B.get(0));
        assertEquals(A.get(4), B.get(4));
    }

    @Test
    public void getTest() {
        ArrayDeque A = new ArrayDeque();
        int N = 9;
        for (int i = 1; i < N; i += 1) {
            A.addLast(i);
            }
        assertEquals(A.get(7), 7);
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
                int S = L.size();
                L.addFirst(randVal);
                assertEquals(L.size(), S + 1);
            } else if (operationNumber == 1) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                int S = L.size();
                L.addLast(randVal);
                assertEquals(L.size(), S + 1);
            } else if (operationNumber == 2) {
                // removeFirst
                if (L.size() > 0) {
                    int S = L.size();
                    L.removeFirst();
                    assertEquals(L.size(), S - 1);
                }
            } else if (operationNumber == 3) {
                // removeLast
                if (L.size() > 0) {
                    int S = L.size();
                    L.removeLast();
                    assertEquals(L.size(), S - 1);
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
