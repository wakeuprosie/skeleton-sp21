package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import static gitlet.Utils.*;

/** This class handles the logic for init
 * Command: init
 * Input: n/a */

public class Repository {

    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMITS_DIR = join(CWD, ".gitlet", "commits");
    public static final File STAGING = join(CWD, ".gitlet", "staging-hashmap");
    public static final File STAGING_RM= join(CWD, ".gitlet", "staging-rm-hashmap");
    public static final File BLOBS_DIR = join(CWD, ".gitlet", "blobs");
    public static final File BRANCHES_DIR = join(CWD, ".gitlet", "branches");
    public static final File MASTER = join(CWD, ".gitlet", "branches", "master");
    public static final File HEAD = join(CWD, ".gitlet", "branches", "head");
    public static final File CURRENT_BRANCH = join(BRANCHES_DIR, "current-branch");

    // Constructor
    public static void setupPersistence() throws IOException {

        // Checks that .gitlet does NOT exist before proceeding
        if (!GITLET_DIR.isFile()) {
            GITLET_DIR.mkdirs();
            STAGING.createNewFile();
            STAGING_RM.createNewFile();
            COMMITS_DIR.mkdirs();
            BLOBS_DIR.mkdirs();
            BRANCHES_DIR.mkdirs();
            MASTER.createNewFile();
            HEAD.createNewFile();
            CURRENT_BRANCH.createNewFile();
            writeContents(CURRENT_BRANCH, "master");
            setUpMotherCommit();
            setUpStaging();
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }

    // Initiate first commit and save in head, master, branches
    private static void setUpMotherCommit() {
        Commit motherCommitObject = new Commit("initial commit", null, null);
        String motherSHA = Utils.sha1(serialize(motherCommitObject));
        File file = Utils.join(COMMITS_DIR, motherSHA);
        File file2 = HEAD;
        File file3 = MASTER;
        writeObject(file, motherCommitObject);
        writeContents(file2, motherSHA);
        writeContents(file3, motherSHA);
    }

    // Initiate staging maps
    private static void setUpStaging() {
        HashMap staging = new HashMap();
        HashMap stagingRm = new HashMap();
        File file = STAGING;
        File file2 = STAGING_RM;
        writeObject(file, staging);
        writeObject(file2, stagingRm);
    }

}
