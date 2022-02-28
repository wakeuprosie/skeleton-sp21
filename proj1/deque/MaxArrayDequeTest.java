package deque;

import edu.princeton.cs.algs4.StdRandom;
import net.sf.saxon.functions.Minimax;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.Integer;
import java.util.Comparator;


/** main test class */
public class MaxArrayDequeTest {

    /** Integer comparator class */
    private static class IntComparator implements Comparator<Integer> {

        /* Compare method of IntComparator */
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);
        }
    }

    /** Method that creates an instance of the IntComparator Comparator object */
    public static Comparator<Integer> getIntComparator(){
        return new IntComparator();
    }

    @Test
    public void Test1(){
        Comparator ci = new MaxArrayDequeTest.IntComparator();

        /** Create an MAD with the IntComparator */
        MaxArrayDeque A = new MaxArrayDeque(ci);
        A.addFirst(1);
        A.addLast(2);
        A.addLast(3);
        A.addLast(4);
        A.addLast(5);
        A.addLast(6);
        A.addLast(7);
        assertEquals(A.max(), 7);
    }


}
