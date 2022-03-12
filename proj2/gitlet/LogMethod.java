package gitlet;

import java.io.File;
import java.util.*;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** This class handles the logic for log
 * Command: log
 * Input: depends on the checkout method called -- more detail below */

public class LogMethod {

    public static void log() {
        // Access the head commit
        File headFile = HEAD_DIR;
        Commit headCommit = readObject(headFile, Commit.class);

        // Check if you're at initial commit
        helper(headCommit);

    }

    private static void helper(Commit object) {

        if (object == null) {
            return;
        }

        // Print commit ID
        System.out.println("===");
        System.out.format("Commit %s", sha1(object));
        System.out.println();
        // Print commit date
        System.out.format("Date: %tT", object.getTime());
        System.out.println();
        // Print commit message
        System.out.println(object.getMessage());

        // Access the parent commit to prep for recursion
        String parentCommitID = object.getParent();

        // Recurse, passing in the parent commit, as long as parent is not null
        if (parentCommitID != null) {
            File blob = join(BLOBS_DIR, parentCommitID);
            Commit parentCommit = readObject(blob, Commit.class);
            helper(parentCommit);
        }

        return;
    }
}
