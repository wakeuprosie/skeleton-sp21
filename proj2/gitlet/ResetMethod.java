package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static gitlet.Repository.*;
import static gitlet.Utils.*;
import static gitlet.Utils.writeObject;

public class ResetMethod {

    public static void reset(String commitID) {
        // Find the matching commit object in commits folder
        // checkout all files tracked by given commit
        // Remove tracked files that are not in this given commit
        // Move branch head to this given commit
        // Clear staging area
        // This command is essentially a checkout for an arbitrary commit

        File inputCommit = join(COMMITS_DIR, commitID);

        /* Failure: No commit with this commitID */
        if (!inputCommit.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }

        checkoutCopy(commitID);

    }

    private static void checkoutCopy(String commitID) {

        File inputCommitFile = join(COMMITS_DIR, commitID);

        // Open the commit with this ID
        Commit inputCommitOpened = readObject(inputCommitFile, Commit.class);

        /* BODY */
        // Access the superFiles hashmap
        HashMap superFiles = inputCommitOpened.superFiles;

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

        // Reassign HEAD to the branch name commit
        File headFile = HEAD;
        writeObject(headFile, inputCommitOpened);

        // Reassign this branch pointer
        String inputCommitBranchName = inputCommitOpened.ownerBranch;
        File branchFile = join(BRANCHES_DIR, inputCommitBranchName);
        writeObject(branchFile, inputCommitOpened);

    }
}
