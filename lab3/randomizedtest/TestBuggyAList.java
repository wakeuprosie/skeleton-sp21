package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE/
    @Test
    public static void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> M = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size2 = M.size();
                assertEquals(size, size2);
            } else if (operationNumber == 2) {
                // getLast
                if (L.size() > 0 &&  M.size() > 0) {
                    int l = L.getLast();
                    int m = M.getLast();
                    assertEquals(l, m);
                    int size = L.size();
                    int size2 = M.size();
                }
            } else if (operationNumber == 3) {
                // removeLast
                if (L.size() > 0 && M.size() > 0) {
                    L.removeLast();
                    M.removeLast();
                    int size = L.size();
                    int size2 = M.size();
                }
            }
        }
    }

  @Test
  public static void testThreeAddThreeRemove() {
      AListNoResizing<Integer> correct = new AListNoResizing<>();
      BuggyAList<Integer> broken = new BuggyAList<>();

      correct.addLast(5);
      correct.addLast(10);
      correct.addLast(15);

      broken.addLast(5);
      broken.addLast(10);
      broken.addLast(15);

      assertEquals(correct.size(), broken.size());

      assertEquals(correct.removeLast(), broken.removeLast());
      assertEquals(correct.removeLast(), broken.removeLast());
      assertEquals(correct.removeLast(), broken.removeLast());
  }

    public static void main(String[] args) {
        randomizedTest();
        testThreeAddThreeRemove();
    }
}
