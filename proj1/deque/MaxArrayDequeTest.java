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

    /** String comparator class */
    private static class StringComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    public static Comparator<String> getStringComparator() {
        return new StringComparator();
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

    @Test
    public void Test2(){
        Comparator ci = new MaxArrayDequeTest.IntComparator();
        Comparator cs = new MaxArrayDequeTest.StringComparator();

        /** Create an MAD with an Int Comparator, but call max with the string comparator */
        MaxArrayDeque A = new MaxArrayDeque(ci);
        A.addFirst("Anna");
        A.addLast("Bobby");
        A.addLast("Cindy");
        A.addLast("Dina");
        A.addLast("Eagore");
        A.addLast("Francis");
        A.addLast("Lola");
        assertEquals(A.max(cs), "Lola");
    }


}
