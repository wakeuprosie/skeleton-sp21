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

    public static void commitMethod(String message) {

        File parent = HEAD;  // Access the commit at HEAD -- the most recent commit in active branch
        Commit parentCommit = readObject(parent, Commit.class); // Deserialize parent commit into an accessible object

        String parentID = sha1(serialize(parentCommit));
        Commit commitObject = new Commit(message, parentID); // Initiate new commit, using the parentID

        String thisCommitID = sha1(serialize(commitObject)); // Generate a SHA for this commit object

        /** HANDLE STAGING FOR ADD */
        // Access staging for add hashmap
        File file = STAGING;
        HashMap stagingHashMap = readObject(file, HashMap.class);

        // Copy staging hashmap to commit tracked files hashmap
        commitObject.trackedFiles.putAll(stagingHashMap);

        // Replace super files with staging hashmap values
        commitObject.superFiles.putAll(stagingHashMap);

        // Clear staging hashmap
        stagingHashMap.clear();

        /** HANDLE STAGING FOR REMOVE */
        // Access staging for remove hashmap
        File removeFile = STAGING_RM;
        HashMap stagingRmHashMap = readObject(removeFile, HashMap.class);

        // Copy staging for remove hashmap to commit removed files hashmap
        commitObject.removedFiles.putAll(stagingRmHashMap);

        // Remove files from commit super files hashmap
        Set rmKeys = stagingRmHashMap.keySet();
        for (Object key : rmKeys) {
            commitObject.superFiles.remove(key);

            // Remove file from CWD
            restrictedDelete((String) key);
        }






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
     * Emptied staging hashmap
     */
}
