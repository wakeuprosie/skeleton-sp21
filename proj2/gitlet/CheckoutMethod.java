package gitlet;

import java.io.File;
import java.util.*;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** This class handles the logic for checkout
 * Command: checkout
 * Input: depends on the checkout method called -- more detail below */

public class CheckoutMethod {

    /* Input: [file name] */
    public static void method1(String filename) {
        File headCommit = Utils.join(CWD, ".gitlet", "branches", "head");
        Commit headCommitOpened = Utils.readObject(headCommit, gitlet.Commit.class);

        // Get the parent commits hash map and the sha of this file
        HashMap trackedFiles = headCommitOpened.trackedFiles;

        if (trackedFiles.get(filename) == null) { // Failure: there is no version of this file in the head commit
            System.out.println("File does not exist in that commit.");
            return;
        }

        String shaValue = (String) trackedFiles.get(filename); // Get the sha of the file in head

        File currentFileOnComputer = join(CWD, filename);

        File blob = join(CWD, ".gitlet", "blobs", shaValue); // Get the sha file contents from blobs
        assert(blob.exists()); // Double-check the file exists in blobs
        writeContents(currentFileOnComputer, blob); // Write over cwd with the file from head

        return;

        // Uhhh what happens to the OG file... I don't even know how you go back to og file on regular git so 0_0

        /** Output:
         * The file of interest in the CWD has been overwritten with the version that was in HEAD
         */

    }

//    public static Commit findTheRightParent(Commit commit, String filename) {
//        HashMap trackedFiles = commit.trackedFiles;
//        Commit rightCommit = commit;
//
//        if (trackedFiles.containsKey(filename)) {
//            return rightCommit;
//        } else {
//            findTheRightParent(commit.getParent());
//        }
//    }

    /* Input: [commit id],[file name] */
    public static void method2() {

    }

    /* Input: [branch name] */
    public static void method3() {

    }

}
