package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import static gitlet.Repository.*;
import static gitlet.Utils.*;
import static gitlet.Utils.writeObject;

/** This class handles the logic for committing
 * Command: ADD
 * Input: one arg of file name */

public class CommitMethod {

    public static void commitMethod(String message) throws IOException {

        // Access the commit at head
        String parentID = readContentsAsString(HEAD);

        // Access staging maps to put in new commit
        File stagingFile = STAGING;
        HashMap stagingHashMap = readObject(stagingFile, HashMap.class);
        File stagingRmFile = STAGING_RM;
        HashMap stagingRmHashMap = readObject(stagingRmFile, HashMap.class);

        // Failure: no files staged for add or remove
        if (stagingHashMap.isEmpty() && stagingRmHashMap.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        // Construct a new commit object
        Commit newCommitObject = new Commit(message, parentID, null);

        /* HANDLE STAGING FOR ADD */
        // Copy staging hashmaps to new commit
        newCommitObject.trackedFiles.putAll(stagingHashMap);
        // If the key in staging doesn't exist in superfiles, add it
        // If the key in staging does exist in superfiles, update it
        newCommitObject.superFiles.putAll(stagingHashMap);

        // Clear staging hashmap and re-save in folder
        stagingHashMap.clear();
        writeObject(STAGING, stagingHashMap);

        /* HANDLE STAGING FOR REMOVE */
        // Check if nothing in staging rm hashmap
        if (!stagingRmHashMap.isEmpty()) {

            // Copy to new commit
            newCommitObject.getRemovedFiles().putAll(stagingRmHashMap);

            // Remove files from commit super files hashmap
            Set rmKeys = stagingRmHashMap.keySet();
            for (Object key : rmKeys) {
                newCommitObject.superFiles.remove(key);
            }
            // Clear staging rm hashmap
            stagingRmHashMap.clear();
            writeObject(STAGING_RM, stagingHashMap);
        }


        /** LOGIC TO FINISH THE COMMIT METHOD */
        // Save new commit in commits folder
        String newCommitID = Utils.sha1(serialize(newCommitObject));
        File newCommitFile = join(COMMITS_DIR, newCommitID); // Create a file for this commit
        newCommitFile.createNewFile();
        writeObject(newCommitFile, newCommitObject); // Save commit object to its place in commits folder

        // Move HEAD pointer
        writeContents(HEAD, newCommitID);

        // Move current branch pointer (change the branch name it holds)
        File currentBranchPointer = join(BRANCHES_DIR, readContentsAsString(CURRENT_BRANCH));
        writeContents(currentBranchPointer, newCommitID);

    }
    /** Output:
     * A new commit that has a reference to its parent commit
     * - the commit has a trackedFiles hashmap that contains a hashmap of ONLY files that got updated with this commit
     * - the commit also has a superFiles hashmap that contains a hashmap of ALL files that exist and the IDs of the latest versions (which actual contents can then be found in blobs)
     * Updated head that contains serialized version of the latest commit on this branch
     * Emptied staging maps
     */
}
