package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE/
    public static void testThreeAddThreeRemove() {
        /* run on no resize class */
        AListNoResizing<Integer> wrong = new AListNoResizing<Integer>();
        BuggyAList<Integer> right = new BuggyAList<Integer>();

        int[] input = {1, 2, 3};
        Boolean[] output = new Boolean[3];
        Boolean[] expected = {true, true, true};
        int index = 0;

        for (int i : input) {
            wrong.addLast(i);
            right.addLast(i);
        }

        int w, r;

        for (int i = 0; i < 2; i += 1) {
            w = wrong.removeLast();
            r = right.removeLast();
            if (w == r) {
                output[i] = true;
                index += 1;
            } else {
                output[i] = false;
                index += 1;
            }
        }

        for (int i = 0; i < 3; i += 1) {
            if (output[i] != expected[i]) {
                System.out.println("Mismatch in position " + i + ", expected " + expected[i] + " but got " + output[i]);
            }
        }
        /* run on resize class */
    }

    public static void main(String[] args) {
        testThreeAddThreeRemove();
    }
}
