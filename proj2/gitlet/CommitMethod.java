package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import static gitlet.Repository.*;
import static gitlet.Utils.*;
import static gitlet.Utils.writeObject;

/** This class handles the logic for committing
 * Command: ADD
 * Input: one arg of file name */

public class CommitMethod {

    public static void commitMethod(String message, String secondParentID) {

        // Access the head commit
        File parent = HEAD;
        Commit parentCommit = readObject(parent, Commit.class);
        String parentID = sha1(serialize(parentCommit));
        Commit commitObject;

        // Construct a new commit object
        if (secondParentID == null) {
            commitObject = new Commit(message, parentID, null);
        } else {
            commitObject = new Commit(message, parentID, secondParentID);
        }

        // Give the new commit an ID
        String thisCommitID = sha1(serialize(commitObject));

        // Access staging maps
        File file = STAGING;
        HashMap stagingHashMap = readObject(file, HashMap.class);
        File removeFile = STAGING_RM;
        HashMap stagingRmHashMap = readObject(removeFile, HashMap.class);

        // Failure: no files staged for add or remove
        if (stagingHashMap.isEmpty() && stagingRmHashMap.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }

        /* HANDLE STAGING FOR ADD */
        // Copy staging hashmap to commit tracked files hashmap
        commitObject.getTrackedFiles().putAll(stagingHashMap);

        // Replace super files with staging hashmap values
        commitObject.getSuperFiles().putAll(stagingHashMap);

        // Clear staging hashmap
        stagingHashMap.clear();

        /* HANDLE STAGING FOR REMOVE */
        // Copy staging for remove hashmap to commit removed files hashmap
        commitObject.getRemovedFiles().putAll(stagingRmHashMap);

        // Remove files from commit super files hashmap
        Set rmKeys = stagingRmHashMap.keySet();
        for (Object key : rmKeys) {
            commitObject.getSuperFiles().remove(key);

            // Remove file from CWD
            restrictedDelete((String) key);
        }
        // Clear staging rm hashmap
        stagingRmHashMap.clear();

        /** LOGIC TO FINISH THE COMMIT METHOD */
        // Create a commit folder
        File thisCommitFolder = Utils.join(COMMITS_DIR, thisCommitID); // Create a file for this commit
        writeObject(thisCommitFolder, commitObject); // Save commit object to its place in commits folder

        // Move HEAD pointer
        File head = HEAD;
        writeObject(head, commitObject);

    }
    /** Output:
     * A new commit that has a reference to its parent commit
     * - the commit has a trackedFiles hashmap that contains a hashmap of ONLY files that got updated with this commit
     * - the commit also has a superFiles hashmap that contains a hashmap of ALL files that exist and the IDs of the latest versions (which actual contents can then be found in blobs)
     * Updated head that contains serialized version of the latest commit on this branch
     * Emptied staging maps
     */
}
