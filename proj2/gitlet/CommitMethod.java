package gitlet;

import java.io.File;
import java.util.HashMap;

import static gitlet.Repository.CWD;
import static gitlet.Repository.STAGING;
import static gitlet.Utils.*;
import static gitlet.Utils.writeObject;

/** This class handles the logic for committing
 * Command: ADD
 * Input: one arg of file name */

public class CommitMethod {

    public static void commitMethod(String message) {

        File parent = Utils.join(CWD, ".gitlet", "branches", "head");  // Access the commit at HEAD -- the most recent commit in active branch
        Commit parentCommit = readObject(parent, Commit.class); // Deserialize parent commit into an accessible object

        String parentID = sha1(serialize(parentCommit));
        Commit commitObject = new Commit(message, parentID); // Initiate new commit, using the parentID

        String thisCommitID = sha1(serialize(commitObject)); // Generate a SHA for this commit object

        // Open staging hashmap for access
        File file = STAGING;
        HashMap stagingHashMap = readObject(file, HashMap.class);

        // Copy staging hashmap to commit tracked files hashmap
        commitObject.trackedFiles.putAll(stagingHashMap);

        // Replace super files with staging hashmap values
        commitObject.superFiles.putAll(stagingHashMap);

        // Clear staging hashmap
        stagingHashMap.clear();

        // Create a commit folder
        File thisCommitFolder = Utils.join(CWD, ".gitlet", "commits", thisCommitID); // Create a file for this commit
        writeObject(thisCommitFolder, commitObject); // Save commit object to its place in commits folder

        // Move HEAD pointer
        File head = join(CWD, ".gitlet", "branches", "head");
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
