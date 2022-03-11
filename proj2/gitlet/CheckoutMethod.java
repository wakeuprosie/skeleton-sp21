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

    /* Input: [commit id],[file name] */
    public static void method2(String commitID, String fileName) {

        File file = join(CWD, fileName);

        // Assert the file exists in the CWD
        if (!file.exists()) {
            System.out.print("No commit with that id exists.");
        }

        // Assert the commit exists in .gitlet/commits
        File commitFile = join(CWD, ".gitlet", "commits", commitID);
        if (!commitFile.exists()) {
            System.out.print("No commit with that id exists.");
        }
        Commit commitOpened = readObject(commitFile, Commit.class); // Confirmed commit exists. Open it for access.

        // Get the file in the commit
        HashMap trackedFiles = commitOpened.trackedFiles;
        String blobID = (String) trackedFiles.get(fileName);
        File blob = join(CWD, ".gitlet", "blobs", blobID);

        // Write over the file in the CWD
        writeContents(file, blob);

    }

    /* Input: [branch name] */
    public static void method3(String branchName) {

        // Open the newest commit at given branch for access
        File branchNameCommit = Utils.join(CWD, ".gitlet", "branches", "branchName");
        Commit branchNameCommitOpened = Utils.readObject(branchNameCommit, gitlet.Commit.class);

        // Access the superFiles hashmap
        HashMap superFiles = branchNameCommitOpened.superFiles;

        // A set of all the filenames that existed up to this commit
        Set<String> allSuperFileNames = superFiles.keySet();

        // Iterator for this set
        Iterator<String> itr = allSuperFileNames.iterator();

        // For every file created up to this commit, overwrite the file in CWD with the version in the set
        while (itr.hasNext()) {
            String key = itr.next();
            String shavalue = (String) superFiles.get(key);
            File fileToOverwrite = join(CWD, key);
            File blob = join(BLOBS_DIR, shavalue);
            writeContents(blob, fileToOverwrite);
        }

        // Delete every file currently in CWD but not in set
        File fileCWD = CWD;
        String[] allFilesInCWD = fileCWD.list();

        for (String str : allFilesInCWD) {
            if (superFiles.get(str) == null) {
                // delete file on CWD
                File fileToClear = join(CWD, str);
                restrictedDelete(fileToClear);
            }
        }

        // Clear staging
        File file = STAGING;
        HashMap stagingHashMap = readObject(file, HashMap.class);
        stagingHashMap.clear();

        // Reassign HEAD to the branchname commit
        File headFile = HEAD_DIR;
        writeObject(headFile, serialize(branchNameCommitOpened));

    }

}
