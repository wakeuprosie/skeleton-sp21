package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static gitlet.Utils.*;
import static gitlet.Commit.*;

/** Gitlet repository.
 *  @author risa
 */

public class Repository {

    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMITS_DIR = join(CWD, ".gitlet", "commits");
    public static final File STAGING = join(CWD, ".gitlet", "staging-hashmap");
    public static final File BLOBS_DIR = join(CWD, ".gitlet", "blobs");
    public static final File BRANCHES_DIR = join(CWD, ".gitlet", "branches");
    public static final File MASTER_DIR = join(CWD, ".gitlet", "branches", "master");
    public static final File HEAD_DIR = join(CWD, ".gitlet", "branches", "head");

    /** Constructor */
    public static void setupPersistence() {
        // Check that .gitlet does NOT exist before proceeding
        if (!(GITLET_DIR.exists())) {
            GITLET_DIR.mkdir();
            STAGING.mkdir();
            COMMITS_DIR.mkdir();
            BLOBS_DIR.mkdir();
            BRANCHES_DIR.mkdir();
            MASTER_DIR.mkdir();
            HEAD_DIR.mkdir();
            setUpMotherCommit();
            setUpStaging();
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }

    /** Initiate mother commit and save in head, master, branches */
    public static void setUpMotherCommit() {
        Commit motherCommitObject = new Commit("initial commit", null);
        String motherSHA = Utils.sha1(motherCommitObject);
        File file = Utils.join(CWD, "commits", motherSHA);
        File file2 = Utils.join(CWD, "head");
        File file3 = Utils.join(CWD, "master");
        writeObject(file, motherCommitObject);
        writeObject(file2, motherCommitObject);
        writeObject(file3, motherCommitObject);
    }

    /** Initiate staging hashmap */
    public static void setUpStaging() {
        HashMap staging = new HashMap();
        File file = Utils.join(CWD, ".gitlet", "staging-hashmap"); // Create a pathname to save the staging hashmap
        writeObject(file, staging); // Read blank hashmap into that pathname
    }

}
