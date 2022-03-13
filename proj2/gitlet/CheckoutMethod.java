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
        File headCommit = HEAD_DIR;
        Commit headCommitOpened = readObject(headCommit, Commit.class);

        // Get the commits hash map and the sha of this file
        HashMap trackedFiles = headCommitOpened.trackedFiles;

        if (trackedFiles.get(filename) == null) { // Failure: there is no version of this file in the head commit
            System.out.println("File does not exist in that commit.");
            return;
        }

        String shaValue = (String) trackedFiles.get(filename); // Get the sha of the file in head

        File currentFileOnComputer = join(CWD, filename);

        File blob = join(BLOBS_DIR, shaValue); // Get the sha file contents from blobs
        assert(blob.exists()); // Double-check the file exists in blobs
        String blobString = readContentsAsString(blob);

        writeContents(currentFileOnComputer, blobString); // Write over cwd with the file from head

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
        File branchNameCommit = Utils.join(BRANCHES_DIR, branchName);

        /* THREE FAILURE CASES */
        /* Failure 1: if branch doesn't exist -- exit */
        if (!branchNameCommit.exists()) {
            System.out.print("No such branch exists.");
            return;
        }
        Commit branchNameCommitOpened = Utils.readObject(branchNameCommit, Commit.class);

        /* Failure 2: if already on the given branch -- exit */
        if (currentBranch == branchName) {
            System.out.println("No need to checkout the current branch.");
            return;
        }

        /* Failure 3: if there is a file in the CWD but NOT in input branch */
        File cwdDir = CWD;
        List<String> cwdList = plainFilenamesIn(cwdDir);

        HashMap branchNameCommitHash = branchNameCommitOpened.superFiles;

        Iterator cwdListIterator = cwdList.iterator();

        while (cwdListIterator.hasNext()) {
            String fileName = (String) cwdListIterator.next();

            if (!branchNameCommitHash.containsKey(fileName)) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
        }

        /* BODY */
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

        // Edit HEAD pointer to the branch name commit
        File headFile = HEAD_DIR;
        writeObject(headFile, branchNameCommitOpened);

        // Edit current branch value to new branch name
        currentBranch = branchName;

    }

}
