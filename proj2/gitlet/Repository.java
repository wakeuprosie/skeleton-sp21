package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import static gitlet.Utils.*;


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
    public static String currentBranch;

    /** Constructor */
    public static void setupPersistence() throws IOException {
        // Check that .gitlet does NOT exist before proceeding
        if (!GITLET_DIR.isFile()) {
            GITLET_DIR.mkdirs();
            STAGING.createNewFile();
            COMMITS_DIR.mkdirs();
            BLOBS_DIR.mkdirs();
            BRANCHES_DIR.mkdirs();
            MASTER_DIR.createNewFile();
            HEAD_DIR.createNewFile();
            currentBranch = "master";
            setUpMotherCommit();
            setUpStaging();
            setUpSuperHashmap();
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }

    /** Initiate mother commit and save in head, master, branches */
    public static void setUpMotherCommit() {
        Commit motherCommitObject = new Commit("initial commit", null);
        String motherSHA = Utils.sha1(serialize(motherCommitObject));
        File file = Utils.join(COMMITS_DIR, motherSHA);
        File file2 = HEAD_DIR;
        File file3 = MASTER_DIR;
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

    /** Initiate super hashmap */
    public static void setUpSuperHashmap() {
        HashMap superHashMap = new HashMap();
        File file = Utils.join(CWD, ".gitlet", "super-hashmap"); // Create a pathname to save the staging hashmap
        writeObject(file, superHashMap); // Read blank hashmap into that pathname

    }

}
