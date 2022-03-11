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

        // Save staging hashmap to this commits hashmap -- remember we are just saving the IDs of the blobs that are the versions of the files we want "saved" with this commit
        File file = Utils.join(CWD, ".gitlet", "staging-hashmap");
        HashMap stagingHashMap = readObject(file, HashMap.class);
        commitObject.updateTrackedFiles(stagingHashMap);

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
     * A new commit that has a reference to its parent commit, a hashmap that contains the IDs of the latest versions of the files it saves (which actual contents can then be found in blobs)
     * Updated head that contains serialized version of the latest commit on this branch
     * Emptied staging hashmap
     */
}
