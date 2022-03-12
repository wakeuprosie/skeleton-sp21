package gitlet;

import java.io.File;
import java.util.HashMap;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class RemoveMethod {

    /** This class handles the logic for removing
     * Command: rm
     * Input: one arg of file name */

    /** Removes from CWD and from HEAD commit */
    public static void remove(String filename) {

        File stagingFile = STAGING;
        File fileInCWD = join(CWD, filename);

        // Failure: if not in staging or in head commit -- print error message
        if (!(stagingFile.exists(filename) && !fileInCWD.exists(filename))) {
            System.out.println("No reason to remove the file.");
        }

        // Remove this file from staging hashmap
        HashMap stagingOpened = readObject(stagingFile, HashMap.class);
        // Remove from staging hashmap if it exists
        stagingOpened.remove(filename);
        // Close up staging hashmap and re-save
        writeObject(stagingFile, stagingOpened);

        // Remove this file from current commit
        File headFile = HEAD_DIR;
        Commit headCommit = readObject(headFile, Commit.class);
        headCommit.trackedFiles.remove(filename);
        headCommit.superFiles.remove(filename);

        // Remove from CWD
        restrictedDelete(fileInCWD);

    }
}
