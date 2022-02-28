package deque;

import edu.princeton.cs.algs4.StdRandom;
import net.sf.saxon.functions.Minimax;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.Integer;

public class MaxArrayDequeTest {

    public static class IntComparator implements Comparator<Integer> {
         public int compare(int a, int b) {
             return a.compareTo(b);
        }
    }

    /** Build a constructor to use for mad test
     * - build the class
     * - initiate an instance of IntComparator
     */

    @Test
    public void Test1(){
        Comparator<Integer> ci = new IntComparator();
        MaxArrayDeque A = new MaxArrayDeque(ci);
        A.max();
    }


}
